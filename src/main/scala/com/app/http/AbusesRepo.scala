package com.app.http

import cats.effect.Effect
import doobie.util.transactor.Transactor
import cats.implicits._

class AbusesRepo[F[_]](transactor: Transactor[F]) {}

object AbusesRepo {

  case class RepoConfig()

  def apply[F[_]: Effect](config: RepoConfig): F[AbusesRepo[F]] = {
    val transactor: F[Transactor[F]] = null.asInstanceOf[Transactor[F]].pure[F] // todo finish
    transactor.map(new AbusesRepo(_))
  }

}
