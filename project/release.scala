import sbt._
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport._

import com.typesafe.sbt.pgp.PgpKeys

object release {

  object repository {
    val snapshot = "karelcemus-snapshot-maven-repository" at "http://maven.karelcemus.cz/artifactory/libs-snapshot/"
    val release = "karelcemus-release-maven-repository" at "http://maven.karelcemus.cz/artifactory/libs-release/"
  }

  def settings =
    common ++ maven ++ release ++ publish

  private lazy val common = Seq(
    homepage := Some( url( "https://edulify.github.io/play-sitemap-module.edulify.com" ) ),
    licenses := Seq( "The Apache Software License, Version 2.0" -> url( "http://www.apache.org/licenses/LICENSE-2.0.txt" ) )
  )

  private lazy val maven = Seq(
    publishArtifact in Test := false,
    publishMavenStyle := true,
    pomIncludeRepository := { _ => true },
    pomExtra :=
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
  )

  private lazy val release = Seq(
    // Release plugin settings
    releaseVersionFile := file( "project/version.sbt" ),
    releaseCrossBuild := true,
    releaseTagName := ( Keys.version in ThisBuild ).value,
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    credentials ++= releaseCredentials.toSeq.flatten
  )

  private lazy val publish = Seq(
    publishTo := {
      if ( isSnapshot.value ) Some( repository.snapshot ) else Some( repository.release )
    }
  )

  private lazy val releaseCredentials = for {
    user <- Option( System.getenv() get "MAVEN_USERNAME" )
    pass <- Option( System.getenv() get "MAVEN_PASSWORD" )
  } yield Seq(
    Credentials( "Artifactory Realm", "maven.karelcemus.cz", user, pass )
  )

}
