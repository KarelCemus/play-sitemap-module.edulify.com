name := "sitemap-module"

scalaVersion := "2.12.8"

scalacOptions := Seq("-feature", "-deprecation")

crossScalaVersions := Seq( "2.11.11", scalaVersion.value )

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  javaCore,
  guice,
  "com.github.dfabulich" % "sitemapgen4j" % "1.1.2",
  "org.reflections" % "reflections" % "0.9.11"
)

organization := "com.github.karelcemus"

organizationName := "Edulify.com"

organizationHomepage := Some(new URL("https://edulify.com"))

author := "Karel Cemus"

github := "KarelCemus/play-sitemap-module.edulify.com"

startYear := Some(2013)

description := "Playframework module to automatically create sitemaps"
