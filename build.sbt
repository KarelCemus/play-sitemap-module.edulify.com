name := "sitemap-module"

version := "2.2.0-SNAPSHOT"

scalaVersion := "2.12.4"

scalacOptions := Seq("-feature", "-deprecation")

crossScalaVersions := Seq( "2.11.11", scalaVersion.value )

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  javaCore,
  guice,
  "com.github.dfabulich" % "sitemapgen4j" % "1.0.6"
)

organization := "com.github.karelcemus"

organizationName := "Edulify.com"

organizationHomepage := Some(new URL("https://edulify.com"))

publishMavenStyle := false

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some(Resolver.url( "karelcemus-snapshot-repository", new URL( "http://maven.karelcemus.cz/artifactory/libs-snapshot/" ) )( Resolver.defaultIvyPatterns ))
  else
    Some(Resolver.url( "karelcemus-release-repository", new URL( "http://maven.karelcemus.cz/artifactory/libs-release/" ) )( Resolver.defaultIvyPatterns ))
}

startYear := Some(2013)

description := "Playframework module to automatically create sitemaps"

licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("https://edulify.github.io/play-sitemap-module.edulify.com"))

pomExtra := {
  <scm>
    <url>https://github.com/edulify/play-sitemap-module.edulify.com</url>
    <connection>scm:git:git@github.com:edulify/play-sitemap-module.edulify.com.git</connection>
    <developerConnection>scm:git:https://github.com/edulify/play-sitemap-module.edulify.com.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>megazord</id>
        <name>Megazord</name>
        <email>contact [at] edulify.com</email>
        <url>https://github.com/megazord</url>
      </developer>
      <developer>
        <id>ranierivalenca</id>
        <name>Ranieri Valença</name>
        <email>ranierivalenca [at] edulify.com</email>
        <url>https://github.com/ranierivalenca</url>
      </developer>
    </developers>
}
