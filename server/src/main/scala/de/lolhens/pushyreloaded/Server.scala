package de.lolhens.pushyreloaded

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.semigroupk._
import com.comcast.ip4s._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.scalatags._
import org.http4s.server.Router
import org.http4s.server.staticcontent.WebjarService.WebjarAsset
import org.http4s.server.staticcontent._

object Server extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder.default[IO]
      .withHost(host"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(service.orNotFound)
      .build
      .use(_ => IO.never)

  def webjarUri(asset: WebjarAsset) =
    s"assets/${asset.library}/${asset.version}/${asset.asset}"

  lazy val service: HttpRoutes[IO] = Router(
    "/assets" -> {
      WebjarServiceBuilder[IO].toRoutes <+>
        ResourceServiceBuilder[IO]("/assets").toRoutes
    },

    "/" -> {
      HttpRoutes.of {
        case request@GET -> Root =>
          Ok(MainPage(webjars.`pushy-reloaded`.webjarAsset))
      }
    }
  )
}
