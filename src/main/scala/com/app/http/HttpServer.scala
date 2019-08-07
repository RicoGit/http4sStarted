package com.app.http

import cats.effect._
import cats.implicits._
import org.http4s.HttpApp

import scala.concurrent.ExecutionContext.Implicits.global

class HttpServer[F[_]](service: AbusesService[F]) {

  val httpApp: HttpApp[F] = ???

}

object HttpServer {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  case class ServerConfig(host: String, port: Int)

  def apply[F[_]: Effect](config: AppConfig): F[HttpServer[F]] =
    for {
      repo <- AbusesRepo(config.database)
      service <- AbusesService(config.service, repo)
    } yield new HttpServer(service)

  object request {}

  object response {}
}
