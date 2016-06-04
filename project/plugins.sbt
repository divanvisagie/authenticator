resolvers ++= Seq(
  Classpaths.sbtPluginReleases,
  "Twitter Maven" at "https://maven.twttr.com"
)

addSbtPlugin("com.twitter" % "scrooge-sbt-plugin" % "4.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")
