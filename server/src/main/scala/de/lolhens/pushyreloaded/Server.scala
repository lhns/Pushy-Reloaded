package de.lolhens.pushyreloaded

import java.net.URLConnection

import cats.effect.{Blocker, ExitCode}
import cats.syntax.semigroupk._
import monix.eval.{Task, TaskApp}
import monix.execution.Scheduler
import org.http4s.HttpRoutes
import org.http4s.dsl.task._
import org.http4s.implicits._
import org.http4s.scalatags._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.staticcontent.WebjarService.WebjarAsset
import org.http4s.server.staticcontent._

object Server extends TaskApp {
  val host = "0.0.0.0"
  val port = 8080

  private def urlConnectionSetDefaultUseCaches(defaultusecaches: Boolean): Unit =
    new URLConnection(null) {
      override def connect(): Unit = ()
    }.setDefaultUseCaches(defaultusecaches)

  override def run(args: List[String]): Task[ExitCode] =
    Task.deferAction { implicit scheduler =>
      urlConnectionSetDefaultUseCaches(false)

      BlazeServerBuilder[Task](scheduler)
        .bindHttp(port, host)
        .withHttpApp(service.orNotFound)
        .resource
        .use(_ => Task.never)
    }


  lazy val resourceScheduler: Scheduler = Scheduler.io(name = "http4s-resources")
  lazy val blocker: Blocker = Blocker.liftExecutionContext(resourceScheduler)

  def webjarUri(asset: WebjarAsset) =
    s"assets/${asset.library}/${asset.version}/${asset.asset}"

  lazy val service: HttpRoutes[Task] = Router(
    "/assets" -> {
      WebjarService2[Task](WebjarService.Config(blocker)) <+>
        ResourceService2[Task](ResourceService.Config("/assets", blocker))
    },

    "/" -> {
      HttpRoutes.of {
        case request@GET -> Root =>
          Ok(MainPage())
      }
    }
  )
}
