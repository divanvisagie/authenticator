import org.flywaydb.sbt.FlywayPlugin.autoImport._
import sbt.Keys._

parallelExecution in ThisBuild := false

lazy val versions = new {
  val finatra = "2.1.5"
  val guice = "4.0"
  val logback = "1.0.13"
  val mockito = "1.9.5"
  val scalatest = "2.2.3"
  val specs2 = "2.3.12"
  val slick = "3.1.1"
  val bijection = "0.9.2"
}



lazy val baseSettings = Seq(
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = true)),
  libraryDependencies ++= Seq(
    "org.mockito" % "mockito-core" % versions.mockito % "test",
    "org.scalatest" %% "scalatest" % versions.scalatest % "test",
    "org.specs2" %% "specs2" % versions.specs2 % "test"
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    "Twitter Maven" at "https://maven.twttr.com"
  ),
  fork in run := true
)

lazy val root = (project in file(".")).
  settings(
    name := """swissguard""",
    organization := "com.swissguard",
    moduleName := "activator-thrift-seed"
  ).
  aggregate(
    idl,
    server)

lazy val idl = (project in file("swiss-guard-idl")).
  settings(baseSettings).
  settings(
    name := "thrift-idl",
    moduleName := "thrift-idl",
    scroogeThriftDependencies in Compile := Seq(
      "finatra-thrift_2.11"
    ),
    libraryDependencies ++= Seq(
      "com.twitter.finatra" %% "finatra-thrift" % versions.finatra
    )
  )

lazy val server = (project in file("server")).
  enablePlugins(JavaServerAppPackaging).
  settings(baseSettings).
  settings(
    name := "thrift-server",
    moduleName := "thrift-server",
    packageName in Docker := "divanvisagie/swissguard",
    flywayUrl := "jdbc:postgresql://localhost:5432/swissguard",
    flywayUser := "postgres",
    flywayPassword := "postgres",
    flywayLocations += "filesystem:database/flyway/sql",
    // dockerBaseImage := "java:alpine",
    libraryDependencies ++= Seq(
      "com.twitter.finatra" %% "finatra-thrift" % versions.finatra,
      "ch.qos.logback" % "logback-classic" % versions.logback,

      // postgres dependencies
      "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
      "com.typesafe.slick" %% "slick" % versions.slick,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      // end of that

      "com.twitter.finatra" %% "finatra-thrift" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-app" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-core" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-server" % versions.finatra % "test",
      "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

      "com.twitter.finatra" %% "finatra-thrift" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-app" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-core" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-modules" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-server" % versions.finatra % "test" classifier "tests"
    )
  ).
  dependsOn(idl)


fork in run := true
