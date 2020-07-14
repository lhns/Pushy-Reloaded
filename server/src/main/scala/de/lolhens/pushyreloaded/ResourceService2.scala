package de.lolhens.pushyreloaded

import java.nio.file.Paths

import cats.data.{Kleisli, OptionT}
import cats.effect.{ContextShift, Sync}
import cats.implicits._
import org.http4s.server.middleware.TranslateUri
import org.http4s.server.staticcontent.ResourceService.Config
import org.http4s.{HttpRoutes, Response, Status, Uri}
import org.log4s.getLogger

import scala.language.higherKinds
import scala.util.control.NoStackTrace
import scala.util.{Failure, Success, Try}

object ResourceService2 {
  private[this] val logger = getLogger

  def apply[F[_]](
                   config: Config[F])(implicit F: Sync[F], cs: ContextShift[F]): HttpRoutes[F] = {
    val basePath = if (config.basePath.isEmpty) "/" else config.basePath
    object BadTraversal extends Exception with NoStackTrace

    Try(Paths.get(basePath)) match {
      case Success(rootPath) =>
        TranslateUri(config.pathPrefix)(Kleisli { request =>
          request.pathInfo.split("/") match {
            case Array(head, segments@_*) if head.isEmpty =>
              OptionT
                .liftF(F.catchNonFatal {
                  segments.foldLeft(rootPath) {
                    case (_, "" | "." | "..") => throw BadTraversal
                    case (path, segment) =>
                      path.resolve(Uri.decode(segment, plusIsSpace = true))
                  }
                })
                .collect {
                  case path if path.startsWith(rootPath) => path
                }
                .flatMap { path =>
                  StaticFile2.fromResource(
                    path.toString,
                    config.blocker,
                    Some(request),
                    preferGzipped = config.preferGzipped
                  )
                }
                .semiflatMap(config.cacheStrategy.cache(request.pathInfo, _))
                .recoverWith {
                  case BadTraversal => OptionT.some(Response(Status.BadRequest))
                }
            case _ =>
              OptionT.none
          }
        })

      case Failure(e) =>
        logger.error(e)(
          s"Could not get root path from ResourceService config: basePath = ${config.basePath}, pathPrefix = ${config.pathPrefix}. All requests will fail.")
        Kleisli(_ => OptionT.pure(Response(Status.InternalServerError)))
    }
  }
}
