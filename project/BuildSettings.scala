import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
    version := "2.0.0-SNAPSHOT",
    homepage := Some(new URL("http://www.helloscala.com/")),
    organization := "supermarket",
    organizationHomepage := Some(new URL("http://helloscala.com/")),
    startYear := Some(2013),
    scalaVersion := "2.11.4",
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-unchecked",
      "-feature",
      "-deprecation"
    ),
    javacOptions := Seq(
      "-encoding", "utf8",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    ),
    offline := true
  )

  lazy val noPublishing = Seq(
    publish :=(),
    publishLocal :=(),
    publishTo := None
  )

}

