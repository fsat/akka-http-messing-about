import sbt.Resolver.bintrayRepo

name := "akka-http-messing-about"

version := "0.0.1"

scalaVersion := Version.scala

libraryDependencies ++= List(
  Library.akkaActor,
  Library.akkaHttp,
  Library.akkaStream,
  Library.mockitoAll % "test",
  Library.scalaTest % "test",
  Library.akkaTestkit % "test"
)


