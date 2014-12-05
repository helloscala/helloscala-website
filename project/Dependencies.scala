import sbt._

object Dependencies {
  def compile(dep: ModuleID): Seq[ModuleID] = compile(Seq(dep))

  def compile(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def provided(dep: ModuleID): Seq[ModuleID] = provided(Seq(dep))

  def provided(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def test(dep: ModuleID): Seq[ModuleID] = test(Seq(dep))

  def test(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def runtime(dep: ModuleID): Seq[ModuleID] = runtime(Seq(dep))

  def runtime(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "runtime")

  def container(dep: ModuleID): Seq[ModuleID] = container(Seq(dep))

  def container(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "container")

  val _scalaModules = Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.2"
  )

  val scalaIOVersion = "0.4.3-1"

  val _scalaIO = Seq(
    "com.github.scala-incubator.io" %% "scala-io-core" % scalaIOVersion,
    "com.github.scala-incubator.io" %% "scala-io-file" % scalaIOVersion)

  val akkaVersion = "2.3.6"

  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val _akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val _akkaTestkit = Seq(
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion)

  val _scalatest = Seq(
    "org.scalatest" %% "scalatest" % "2.2.1"
  )

  val _specs2 = Seq(
    "org.specs2" %% "specs2" % "2.4.2"
  )

  val seleniumVersion = "2.42.1"
  val _selenium = "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion

  val slickVersion = "2.1.0"
  val _slick = "com.typesafe.slick" %% "slick" % slickVersion
  val _slickExtensions = "com.typesafe.slick" %% "slick-extensions" % slickVersion

  val slickPgVersion = "0.6.5.3"
  val _slickPg = Seq(
    "com.github.tminglei" %% "slick-pg_joda-time" % slickPgVersion,
    "com.github.tminglei" %% "slick-pg_json4s" % slickPgVersion,
    "com.github.tminglei" %% "slick-pg" % slickPgVersion
  )

  val _salat = "com.novus" %% "salat" % "1.9.9"

  val _casbah = "org.mongodb" %% "casbah" % "2.7.1"

  val _mongoJava = "org.mongodb" % "mongo-java-driver" % "2.12.1"

  val sprayVersion = "1.3.2"

  val _sprayIo = "io.spray" %% "spray-io" % sprayVersion

  val _sprayClient = "io.spray" %% "spray-client" % sprayVersion

  val _sprayCan = "io.spray" %% "spray-can" % sprayVersion

  val _sprayRouting = "io.spray" %% "spray-routing" % sprayVersion

  val _sprayCaching = "io.spray" %% "spray-caching" % sprayVersion

  val _sprayServlet = "io.spray" %% "spray-servlet" % sprayVersion

  val _scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

  val _markwrap = "org.clapper" %% "markwrap" % "1.0.2"

  val json4sVersion = "3.2.10"
  val _json4s = Seq(
    "org.json4s" %% "json4s-mongo" % json4sVersion,
    "org.json4s" %% "json4s-ext" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion)

  val jettyVersion = "9.1.5.v20140505"
  val _jetty = Seq(
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
    "org.eclipse.jetty" % "jetty-plus" % jettyVersion
  )

  val tomcatVersion = "7.0.56"

  val _tomcat = Seq(
    "org.apache.tomcat.embed" % "tomcat-embed-core" % tomcatVersion,
    "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % tomcatVersion,
    "org.apache.tomcat.embed" % "tomcat-embed-jasper" % tomcatVersion
  )

  val _servlet31 = "javax.servlet" % "javax.servlet-api" % "3.1.0"

  val _tomcatJdbc = "org.apache.tomcat" % "tomcat-jdbc" % tomcatVersion

  val _druid = "com.alibaba" % "druid" % "1.0.9"

  val _netty = "io.netty" % "netty" % "4.0.23.Final"

  val _ehcache = "net.sf.ehcache" % "ehcache-core" % "2.9.0"

  val _slf4j = "org.slf4j" % "slf4j-api" % "1.7.7"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.2"

  val _googleHashMap = "com.googlecode.concurrentlinkedhashmap" % "concurrentlinkedhashmap-lru" % "1.4"

  val _guava = "com.google.guava" % "guava" % "18.0"

  val _bouncycastle = "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.51"

  val _jodaTime = Seq(
    "joda-time" % "joda-time" % "2.5",
    "org.joda" % "joda-convert" % "1.7")

  val _quartz = "org.quartz-scheduler" % "quartz" % "2.2.1"

  val _itextpdf = Seq(
    "com.itextpdf" % "itext-asian" % "5.2.0",
    "com.itextpdf" % "itextpdf" % "5.5.3")

  val _xmlworker = "com.itextpdf.tool" % "xmlworker" % "5.5.3"

  val _javaxMail = "javax.mail" % "javax.mail-api" % "1.5.2"

  val _javaxActivation = "javax.activation" % "activation" % "1.1.1"

  val _javaxJta = "javax.transaction" % "jta" % "1.1"

  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.3.3"

  val _commonsIo = "commons-io" % "commons-io" % "2.4"

  val _commonsImage = "org.apache.commons" % "commons-imaging" % "1.0-SNAPSHOT"

  val _commonsNet = "commons-net" % "commons-net" % "3.3"

  val _commonsCompress = "org.apache.commons" % "commons-compress" % "1.9"

  val _commonsCodec = "commons-codec" % "commons-codec" % "1.9"

  val _jfreechart = "org.jfree" % "jfreechart" % "1.0.19"

  val apachePoiVersion = "3.10.1"
  val _apachePoi = Seq(
    "org.apache.poi" % "poi" % apachePoiVersion,
    "org.apache.poi" % "poi-scratchpad" % apachePoiVersion,
    "org.apache.poi" % "poi-ooxml" % apachePoiVersion,
    "org.apache.poi" % "poi-ooxml-schemas" % apachePoiVersion)

  val _jmimemagic = "net.sf.jmimemagic" % "jmimemagic" % "0.1.3"

  val _h2 = "com.h2database" % "h2" % "1.4.182"

  val _mysql = "mysql" % "mysql-connector-java" % "5.1.33"

  val _postgresql = "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"

  val _cardme = "net.sourceforge.cardme" % "cardme" % "0.4.0"

  val httlVersion = "1.0.11"
  val _httl = "com.github.httl" % "httl" % httlVersion
}

