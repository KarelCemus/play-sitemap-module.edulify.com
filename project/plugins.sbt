// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += Resolver.typesafeRepo("releases")

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.1")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.11")

// PGP signature
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.1")

// looks up new versions of SBT dependencies, check for dependencies updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.4.0")

