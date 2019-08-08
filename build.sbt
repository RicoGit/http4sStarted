name := "http4sStarter"
version := "0.1"
scalaVersion := "2.12.8"
scalafmtOnCompile := true

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
  )

val Http4sVersion = "0.20.0"
val CirceVersion  = "0.11.1"

val pureconfig           = "com.github.pureconfig" %% "pureconfig" % "0.11.1"
val pureconfigCatsEffect = "com.github.pureconfig" %% "pureconfig-cats-effect" % "0.11.1"
val scalameter           = "com.storm-enroute" %% "scalameter" % "0.8.2"

val http4sClient = "org.http4s" %% "http4s-blaze-client" % Http4sVersion
val http4sServer = "org.http4s" %% "http4s-blaze-server" % Http4sVersion
val http4sDsl    = "org.http4s" %% "http4s-dsl" % Http4sVersion
val http4sCirce  = "org.http4s" %% "http4s-circe" % Http4sVersion

val catsCore   = "org.typelevel" %% "cats-core" % "1.6.1"
val catsFree   = "org.typelevel" %% "cats-free" % "1.6.1"
val catsEffect = "org.typelevel" %% "cats-effect" % "1.3.1"

val circeCore       = "io.circe" %% "circe-core" % CirceVersion
val circeDerivation = "io.circe" %% "circe-derivation" % "0.12.0-M1"
val circeGeneric    = "io.circe" %% "circe-generic" % CirceVersion
val circleJava8time = "io.circe" %% "circe-java8" % CirceVersion

val doobieCore     = "org.tpolecat" %% "doobie-core" % "0.7.0"
val doobieHikari   = "org.tpolecat" %% "doobie-hikari" % "0.7.0"
val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % "0.7.0"

libraryDependencies ++= Seq(
  pureconfig,
  pureconfigCatsEffect,
  catsCore,
  catsEffect,
  circeCore,
  circeDerivation,
  circeGeneric,
  circleJava8time,
  http4sClient,
  http4sServer,
  http4sDsl,
  http4sCirce,
  doobieCore,
  doobieHikari,
  doobiePostgres,
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")

// generates scala Http4s rest server by openApi specification
guardrailTasks in Compile := List(
  ScalaServer(
    specPath = (Compile / resourceDirectory).value / "api.yaml",
    pkg = "com.library.http",
    framework = "http4s",
    tracing = false
    )
  )