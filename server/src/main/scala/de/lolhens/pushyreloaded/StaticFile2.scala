package de.lolhens.pushyreloaded

import java.net.URL

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, Sync}
import cats.implicits._
import fs2.io.readInputStream
import org.http4s.StaticFile.DefaultBufferSize
import org.http4s.Status.NotModified
import org.http4s._
import org.http4s.headers.{`Content-Length`, `If-Modified-Since`, `Last-Modified`, `Transfer-Encoding`, _}

import scala.language.higherKinds

object StaticFile2 {
  def fromResource[F[_] : Sync : ContextShift](
                                                name: String,
                                                blocker: Blocker,
                                                req: Option[Request[F]] = None,
                                                preferGzipped: Boolean = false,
                                                classloader: Option[ClassLoader] = None): OptionT[F, Response[F]] = {
    val loader = classloader.getOrElse(getClass.getClassLoader)

    val tryGzipped = preferGzipped && req.flatMap(_.headers.get(`Accept-Encoding`)).exists {
      acceptEncoding =>
        acceptEncoding.satisfiedBy(ContentCoding.gzip) || acceptEncoding.satisfiedBy(
          ContentCoding.`x-gzip`)
    }
    val normalizedName = name.split("/").filter(_.nonEmpty).mkString("/")

    def getResource(name: String) =
      OptionT(Sync[F].delay(Option(loader.getResource(name))))

    val gzUrl: OptionT[F, URL] =
      if (tryGzipped) getResource(normalizedName + ".gz") else OptionT.none

    gzUrl
      .flatMap { url =>
        // Guess content type from the name without ".gz"
        val contentType = nameToContentType(normalizedName)
        val headers = `Content-Encoding`(ContentCoding.gzip) :: contentType.toList

        fromURL(url, blocker, req).map(_.removeHeader(`Content-Type`).putHeaders(headers: _*))
      }
      .orElse(getResource(normalizedName)
        .flatMap(fromURL(_, blocker, req)))
  }

  def fromURL[F[_]](url: URL,
                    blocker: Blocker,
                    req: Option[Request[F]] = None)
                   (implicit
                    F: Sync[F],
                    cs: ContextShift[F]): OptionT[F, Response[F]] =
    OptionT.liftF(F.delay {
      val urlConn = url.openConnection
      val lastmod = HttpDate.fromEpochSecond(urlConn.getLastModified / 1000).toOption
      val ifModifiedSince = req.flatMap(_.headers.get(`If-Modified-Since`))
      val expired = (ifModifiedSince, lastmod).mapN(_.date < _).getOrElse(true)

      if (expired) {
        val lastModHeader: List[Header] = lastmod.map(`Last-Modified`(_)).toList
        val contentType = nameToContentType(url.getPath).toList
        val len = urlConn.getContentLengthLong
        val lenHeader =
          if (len >= 0) `Content-Length`.unsafeFromLong(len)
          else `Transfer-Encoding`(TransferCoding.chunked)
        val headers = Headers(lenHeader :: lastModHeader ::: contentType)

        Response(
          headers = headers,
          body = readInputStream[F](F.delay(urlConn.getInputStream), DefaultBufferSize, blocker)
        )
      } else {
        urlConn.getInputStream.close()
        Response(NotModified)
      }
    })

  private def nameToContentType(name: String): Option[`Content-Type`] =
    name.lastIndexOf('.') match {
      case -1 => None
      case i => MediaType.forExtension(name.substring(i + 1)).map(`Content-Type`(_))
    }
}
