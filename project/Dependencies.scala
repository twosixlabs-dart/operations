import sbt._

object Dependencies {

    val slf4jVersion = "1.7.20"
    val logbackVersion = "1.2.9"
    val betterFilesVersion = "3.8.0"

    val jacksonVersion = "2.9.9"

    val scalaMockVersion = "4.2.0"
    val scalaTestVersion = "3.0.5"

    val dartCommonsVersion = "3.0.307"

    val logging = Seq( "org.slf4j" % "slf4j-api" % slf4jVersion,
                       "ch.qos.logback" % "logback-classic" % logbackVersion )

    val betterFiles = Seq( "com.github.pathikrit" %% "better-files" % betterFilesVersion )

    /// testing
    val scalaMock = Seq( "org.scalamock" %% "scalamock" % scalaMockVersion )

    val jsonValidator = Seq( "org.everit.json" % "org.everit.json.schema" % "1.5.1" )

    val dartCommons = Seq( "com.twosixlabs.dart" %% "dart-json" % dartCommonsVersion,
                           "com.twosixlabs.dart" %% "dart-sql" % dartCommonsVersion,
                           "com.twosixlabs.dart" %% "dart-test-base" % dartCommonsVersion % Test )

}
