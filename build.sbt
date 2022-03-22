import Dependencies._
import sbt._

organization in ThisBuild := "com.twosixlabs.dart.operations"
name := "operations"
scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq( "Maven Central" at "https://repo1.maven.org/maven2/",
                                "JCenter" at "https://jcenter.bintray.com",
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
//publishMavenStyle := true
test in publish in ThisBuild := {}

javacOptions in ThisBuild ++= Seq( "-source", "1.8", "-target", "1.8" )
scalacOptions in ThisBuild += "-target:jvm-1.8"

sonatypeProfileName := "com.twosixlabs"
inThisBuild(List(
    organization := organization.value,
    homepage := Some(url("https://github.com/twosixlabs-dart/operations")),
    licenses := List("GNU-Affero-3.0" -> url("https://www.gnu.org/licenses/agpl-3.0.en.html")),
    developers := List(
        Developer(
            "twosixlabs-dart",
            "Two Six Technologies",
            "",
            url("https://github.com/twosixlabs-dart")
            )
        )
    ))

ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
