package com.app.http

import cats.Applicative
import cats.effect._
import cats.implicits._
import com.library.http.book.{BookHandler, BookResource, GetBookResponse}
import com.library.http.definitions.{Author, Book, BookResponse}
import org.http4s.HttpApp
import org.http4s.implicits._

import scala.concurrent.ExecutionContext.Implicits.global

class HttpServer[F[_]: Async: Applicative](service: AbusesService[F]) extends BookHandler[F] {

  val httpApp: HttpApp[F] = new BookResource().routes(this).orNotFound

  override def getBook(respond: GetBookResponse.type)(name: Option[String] = None): F[GetBookResponse] = {
    for {
      message <- s"Hello, ${name.getOrElse("world")}".pure[F]
    } yield {
      val book = Book(
        1,
        Vector(Author(10, "Carl", "Sagan")),
        "Cosmos",
        Some("Cosmos is a 1980 popular science book by astronomer and Pulitzer Prize-winning author Carl Sagan."),
        Some(1980)
      )
      respond.Ok(BookResponse(Some(book)))
    }
  }

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
