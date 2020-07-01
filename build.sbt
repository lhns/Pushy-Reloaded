name := "Pushy-Reloaded"
version := "2.0.1-SNAPSHOT"

scalaVersion := "2.13.3"

mainClass := Some("greenfoot.export.GreenfootScenarioApplication")

lazy val osName: String =
  if (scala.util.Properties.isLinux) "linux"
  else if (scala.util.Properties.isMac) "mac"
  else if (scala.util.Properties.isWin) "win"
  else throw new Exception("Unknown platform!")

libraryDependencies ++= Seq(
  "org.openjfx" % "javafx-base" % "14.0.1" classifier osName,
  "org.openjfx" % "javafx-controls" % "14.0.1" classifier osName,
  "org.openjfx" % "javafx-graphics" % "14.0.1" classifier osName,
  "org.openjfx" % "javafx-media" % "14.0.1" classifier osName,
  "org.openjfx" % "javafx-swing" % "14.0.1" classifier osName,
)
