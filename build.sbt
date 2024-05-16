val V = new {
  val cats = "2.6.0"
  val catsEffect = "3.5.4"
  val http4s = "0.23.26"
  val http4sScalatags = "0.25.2"
  val logbackClassic = "1.5.3"
  val scalajsDom = "1.1.0"
}

lazy val commonSettings: Seq[Setting[_]] = Seq(
  name := "Pushy-Reloaded",
  version := "2.0.1-SNAPSHOT",

  scalaVersion := "2.13.14",

  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),

  version := {
    val tagPrefix = "refs/tags/"
    sys.env.get("CI_VERSION").filter(_.startsWith(tagPrefix)).map(_.drop(tagPrefix.length)).getOrElse(version.value)
  }
)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(
    publishArtifact := false
  )
  .aggregate(server)

lazy val frontend = project
  .enablePlugins(ScalaJSWebjarPlugin)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % V.cats,
      "org.typelevel" %%% "cats-effect" % V.catsEffect,
      "org.scala-js" %%% "scalajs-dom" % V.scalajsDom,
    ),

    scalaJSLinkerConfig := scalaJSLinkerConfig.value.withESFeatures(_.withESVersion(org.scalajs.linker.interface.ESVersion.ES5_1)),
    scalaJSUseMainModuleInitializer := true,
  )

lazy val frontendWebjar = frontend.webjar
  .settings(
    webjarAssetReferenceType := Some("http4s"),
    libraryDependencies += "org.http4s" %% "http4s-server" % V.http4s
  )

lazy val server = project
  .enablePlugins(BuildInfoPlugin)
  .dependsOn(frontendWebjar)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % V.logbackClassic,
      "org.http4s" %% "http4s-ember-server" % V.http4s,
      "org.http4s" %% "http4s-circe" % V.http4s,
      "org.http4s" %% "http4s-dsl" % V.http4s,
      "org.http4s" %% "http4s-scalatags" % V.http4sScalatags,
      "org.typelevel" %% "cats-effect" % V.catsEffect,
    )
  )
