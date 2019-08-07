package com.app.http

import cats.Applicative
import cats.effect.Effect
import AbusesService.ServiceConfig

class AbusesService[F[_]](config: ServiceConfig, repo: AbusesRepo[F]) {}

object AbusesService {

  case class ServiceConfig()

  def apply[F[_]: Effect](config: ServiceConfig, repo: AbusesRepo[F]): F[AbusesService[F]] =
    Applicative[F].pure(new AbusesService[F](config, repo))

}
