name := "sitemap-module"

scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen" // Warn when numerics are widened.
)

// Allow dead code in tests (to support using mockito).
scalacOptions in Test ~= { (options: Seq[String]) => options filterNot ( _ == "-Ywarn-dead-code" ) }

javacOptions ++= Seq(
  "-Xlint:deprecation"
)

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
