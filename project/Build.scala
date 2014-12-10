import sbt.Keys._
import sbt._
import xerial.sbt.Pack._

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // super market
  ///////////////////////////////////////////////////////////////
  lazy val helloscala = Project("helloscala", file("."))
    .aggregate(
      helloscalaSite,
      ybjUtil)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)

  ///////////////////////////////////////////////////////////////
  // projects
  ///////////////////////////////////////////////////////////////
  lazy val helloscalaSite = myProject("helloscala-site", file("app/site"))
    .dependsOn(ybjUtil)
    .settings(packAutoSettings: _*)
    .settings(
      description := "HelloScala-Site",
      packMain := Map("helloscala" -> "com.helloscala.site.bootstrap.Boot"),
      packExtraClasspath := Map("helloscala" -> Seq("${PROG_HOME}/etc")),
      packResourceDir += (baseDirectory.value / "src/main/webapp" -> "webapp"),
      packResourceDir += (baseDirectory.value / "src/main/etc" -> "etc"),
      packJvmOpts := Map("helloscala" -> Seq("-Dsite.run_mode=production")),
      unmanagedClasspath in Runtime += baseDirectory.value / "src/main/etc",
      unmanagedClasspath in Test += baseDirectory.value / "src/main/etc",
      libraryDependencies ++= (
        compile(_commonsEmail) ++
          compile(_commonsCompress) ++
          compile(_sprayCaching) ++
          compile(_sprayClient) ++
          compile(_sprayRouting) ++
          compile(_httl) ++
          compile(_slick) ++
          compile(_slickPg) ++
          compile(_postgresql) ++
          compile(_markwrap) ++
          compile(_commonsCodec)))

  ///////////////////////////////////////////////////////////////
  // yangbajing projects
  ///////////////////////////////////////////////////////////////

  lazy val ybjUtil = myProject("ybj-util", file("core/util"))
    .settings(
      description := "羊八井平台-实用工具库",
      libraryDependencies ++= (
        provided(_apachePoi) ++
          provided(_commonsCodec) ++
          provided(_commonsEmail) ++
          provided(_salat) ++
          provided(_mongoJava) ++
          compile(_scalaIO) ++
          compile(_json4s) ++
          compile(_jodaTime) ++
          compile(_bouncycastle) ++
          compile(_akkaActor)))

  private def myProject(id: String, base: File): Project =
    Project(id, base)
      .settings(basicSettings: _*)
      .settings(
        resolvers ++= Seq(
          "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
          "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
          "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
          "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
        libraryDependencies ++= (
          compile(_scalaModules) ++
            //            compile(_slf4j) ++
            compile(_logback) ++
            compile(_scalaLogging) ++
            test(_scalatest)))
}

