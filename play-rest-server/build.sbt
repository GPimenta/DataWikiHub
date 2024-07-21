name := """play-rest-server"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  guice,
  "org.postgresql" % "postgresql" % "42.7.3",
//  "org.playframework" %% "play-slick" % "6.1.0",
//  "org.playframework" %% "play-slick-evolutions" % "6.1.0",
//  "jakarta.persistence" % "jakarta.persistence-api" % "3.0.0",
  "org.hibernate" % "hibernate-core" % "5.4.32.Final",
  "net.jodah" % "failsafe" % "2.4.4",
  "io.dropwizard.metrics" % "metrics-core" % "4.2.26",
  javaJpa
)