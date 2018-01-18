// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += "iBiblio Maven" at "http://mirrors.ibiblio.org/maven2/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % System.getProperty("play.version", "2.6.11"))

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

// looks up new versions of SBT dependencies, check for dependencies updates
addSbtPlugin( "com.timushev.sbt" % "sbt-updates" % "0.3.4" )

addSbtPlugin( "com.github.gseitz" % "sbt-release" % "1.0.7" )
