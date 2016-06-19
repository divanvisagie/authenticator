resolvers ++= Seq(
  Classpaths.sbtPluginReleases,
  "Twitter Maven" at "https://maven.twttr.com",
  "Flyway" at "https://flywaydb.org/repo"
)

addSbtPlugin("com.twitter" % "scrooge-sbt-plugin" % "4.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.0.3")

