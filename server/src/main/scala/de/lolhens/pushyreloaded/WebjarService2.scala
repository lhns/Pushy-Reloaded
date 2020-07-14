package de.lolhens.pushyreloaded

import java.nio.file.{Path, Paths}

import cats.data.{Kleisli, OptionT}
import cats.effect.{ContextShift, Sync}
import cats.implicits._
import org.http4s._
import org.http4s.server.staticcontent.WebjarService.{Config, WebjarAsset}

import scala.jdk.CollectionConverters._
import scala.language.higherKinds
import scala.util.control.NoStackTrace

object WebjarService2 {
  /**
   * Creates a new [[HttpRoutes]] that will filter the webjars
   *
   * @param config The configuration for this service
   * @return The HttpRoutes
   */
  def apply[F[_]](config: Config[F])(implicit F: Sync[F], cs: ContextShift[F]): HttpRoutes[F] = {
    object BadTraversal extends Exception with NoStackTrace
    val Root = Paths.get("")
    Kleisli {
      // Intercepts the routes that match webjar asset names
      case request if request.method == Method.GET =>
        request.pathInfo.split("/") match {
          case Array(head, segments@_*) if head.isEmpty =>
            OptionT
              .liftF(F.catchNonFatal {
                segments.foldLeft(Root) {
                  case (_, "" | "." | "..") => throw BadTraversal
                  case (path, segment) =>
                    path.resolve(Uri.decode(segment, plusIsSpace = true))
                }
              })
              .subflatMap(toWebjarAsset)
              .filter(config.filter)
              .flatMap(serveWebjarAsset(config, request)(_))
              .recover {
                case BadTraversal => Response(Status.BadRequest)
              }
          case _ => OptionT.none
        }
      case _ => OptionT.none
    }
  }

  /**
   * Returns an Option(WebjarAsset) for a Request, or None if it couldn't be mapped
   *
   * @param p The request path without the prefix
   * @return The WebjarAsset, or None if it couldn't be mapped
   */
  private def toWebjarAsset(p: Path): Option[WebjarAsset] = {
    val count = p.getNameCount
    if (count > 2) {
      val library = p.getName(0).toString
      val version = p.getName(1).toString
      val asset = p.subpath(2, count).iterator().asScala.mkString("/")
      Some(WebjarAsset(library, version, asset))
    } else
      None
  }

  /**
   * Returns an asset that matched the request if it's found in the webjar path
   *
   * @param webjarAsset The WebjarAsset
   * @param config      The configuration
   * @param request     The Request
   * @return Either the the Asset, if it exist, or Pass
   */
  private def serveWebjarAsset[F[_] : Sync : ContextShift](config: Config[F], request: Request[F])(
    webjarAsset: WebjarAsset): OptionT[F, Response[F]] =
    StaticFile2
      .fromResource(pathInJar(webjarAsset), config.blocker, Some(request))
      .semiflatMap(config.cacheStrategy.cache(request.pathInfo, _))

  def pathInJar(webjarAsset: WebjarAsset): String = {
    import webjarAsset._
    s"/META-INF/resources/webjars/$library/$version/$asset"
  }
}
