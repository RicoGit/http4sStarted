package com.app.http

import cats.effect.{ExitCode, IO, IOApp}
import AbusesRepo.RepoConfig
import AbusesService.ServiceConfig
import HttpServer.ServerConfig
import org.http4s.server.blaze.BlazeServerBuilder
import pureconfig.module.catseffect.loadConfigF
import pureconfig.generic.auto._

object App extends IOApp {

  /**
    * An entry point of application, creates all necessary instances and starts
    * Http server. An IOApp runs the process and adds a JVM shutdown hook to
    * interrupt the infinite process and gracefully shut down your server when
    * a SIGTERM is received.
    * */
  override def run(args: List[String]): IO[ExitCode] =
    for {
      config <- loadConfigF[IO, AppConfig]("app")
      server <- HttpServer[IO](config)
      _ <- BlazeServerBuilder[IO]
        .bindHttp(config.server.port, config.server.host)
        .withHttpApp(server.httpApp)
        .serve
        .compile
        .drain
    } yield ExitCode.Success

}
case class AppConfig(server: ServerConfig, service: ServiceConfig, database: RepoConfig)
