import Dependencies._
import sbt._

organization in ThisBuild := "com.twosixlabs.dart.operations"
name := "operations"
scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq( "Maven Central" at "https://repo1.maven.org/maven2/",
                                "JCenter" at "http://jcenter.bintray.com",
                                "Local Ivy Repository" at s"file://${System.getProperty( "user.home" )}/.ivy2/local/default" )

crossScalaVersions in ThisBuild := Seq( "2.11.12", "2.12.7" )

lazy val root = ( project in file( "." ) ).aggregate( api, client )

lazy val api = ( project in file( "status-api" ) ).settings( libraryDependencies ++= dartCommons
                                                                                     ++ logging
                                                                                     ++ scalaMock,
                                                             excludeDependencies ++= Seq( ExclusionRule( "org.slf4j", "slf4j-log4j12" ),
                                                                                  ExclusionRule( "org.slf4j", "log4j-over-slf4j" ),
                                                                                  ExclusionRule( "log4j", "log4j" ),
                                                                                  ExclusionRule( "org.apache.logging.log4j", "log4j-core" ) ) )

lazy val client = ( project in file( "status-client" ) ).settings( libraryDependencies ++= dartCommons
                                                                                           ++ scalaMock,
                                                                   excludeDependencies ++= Seq( ExclusionRule( "org.slf4j", "slf4j-log4j12" ),
                                                                                  ExclusionRule( "org.slf4j", "log4j-over-slf4j" ),
                                                                                  ExclusionRule( "log4j", "log4j" ),
                                                                                  ExclusionRule( "org.apache.logging.log4j", "log4j-core" ) ) ).dependsOn( api )

// publish configurations
publishMavenStyle := true
test in publish in ThisBuild := {}

sources in ThisBuild in(Compile, doc) := Seq.empty // don't run scaladoc stuff because it generates useless exceptions
publishArtifact in ThisBuild in(Compile, packageDoc) := false

javacOptions in ThisBuild ++= Seq( "-source", "1.8", "-target", "1.8" )
scalacOptions in ThisBuild += "-target:jvm-1.8"
