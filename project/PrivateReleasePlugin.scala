import sbt._
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport._
import com.typesafe.sbt.pgp.PgpKeys
import sbt.plugins.JvmPlugin
import sbtrelease.ReleasePlugin

object PrivateReleasePlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires: Plugins = ReleasePlugin

  object autoImport {
    val author = settingKey[ String ]( "author of the library" )
    val github = settingKey[ String ]( "github repository" )
  }

  import autoImport._

  override def projectSettings = common ++ maven ++ release ++ publish

  object repository {
    val snapshot = "karelcemus-snapshot-maven-repository" at "https://maven.karelcemus.cz/artifactory/libs-snapshot/"
    val release = "karelcemus-release-maven-repository" at "https://maven.karelcemus.cz/artifactory/libs-release/"
  }

  private lazy val common = Seq(
    homepage := Some( url( "https://github.com/" + github.value ) ),
    licenses := Seq( "Apache 2" -> url( "http://www.apache.org/licenses/LICENSE-2.0" ) )
  )

  private lazy val maven = Seq(
    publishMavenStyle := true,
    pomIncludeRepository := { _ => true },
    pomExtra :=
      <scm>
        <url>git@github.com:{ github.value }.git</url>
        <connection>scm:git@github.com:{ github.value }.git</connection>
      </scm>
      <developers>
        <developer>
          <name>{ author.value }</name>
        </developer>
      </developers>
  )

  private lazy val release = Seq(
    // Release plugin settings
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
    user <- Option( System.getenv( ) get "MAVEN_USERNAME" )
    pass <- Option( System.getenv( ) get "MAVEN_PASSWORD" )
  } yield Seq(
    Credentials( "Artifactory Realm", "maven.karelcemus.cz", user, pass )
  )
}
