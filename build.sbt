lazy val commonSettings: Seq[Setting[_]] = Seq(
  name := "Pushy-Reloaded",
  version := "2.0.1-SNAPSHOT",

  scalaVersion := "2.13.5",

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
      "org.typelevel" %%% "cats-core" % "2.6.0",
      "io.monix" %%% "monix" % "3.3.0",
      "org.scala-js" %%% "scalajs-dom" % "1.1.0",
    ),

    scalaJSLinkerConfig := scalaJSLinkerConfig.value.withESFeatures(_.withUseECMAScript2015(false)),
    scalaJSUseMainModuleInitializer := true,
  )

lazy val server = project
  .enablePlugins(BuildInfoPlugin)
  .dependsOn(frontend.webjar)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.11",
      "io.monix" %% "monix" % "3.3.0",
      "org.http4s" %% "http4s-blaze-server" % "0.21.33",
      "org.http4s" %% "http4s-circe" % "0.21.33",
      "org.http4s" %% "http4s-dsl" % "0.21.33",
      "org.http4s" %% "http4s-scalatags" % "0.21.33",
    ),

    buildInfoKeys := Seq(
      "frontendAsset" -> (frontend / Compile / webjarMainResourceName).value,
      "frontendName" -> (frontend / normalizedName).value,
      "frontendVersion" -> (frontend / version).value,
    ),
  )
