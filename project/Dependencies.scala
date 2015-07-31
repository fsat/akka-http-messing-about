import sbt._
import sbt.Resolver.bintrayRepo

object Version {
  val akka                   = "2.3.11"
  val akkaHttp               = "1.0"
  val akkaStream             = "1.0"

  val scala                  = "2.11.7"
  val scalaXml               = "1.0.5"
  val scalaTest              = "2.2.4"
  val mockito                = "1.9.5"
}

object Library {
  val akkaActor               = "com.typesafe.akka"      %% "akka-actor"                     % Version.akka
  val akkaHttp                = "com.typesafe.akka"      %% "akka-http-experimental"         % Version.akkaHttp
  val akkaStream              = "com.typesafe.akka"      %% "akka-stream-experimental"       % Version.akkaStream
  val scalaXml                = "org.scala-lang.modules" %% "scala-xml"                      % Version.scalaXml
  val mockitoAll              = "org.mockito"            %  "mockito-all"                    % Version.mockito
  val scalaTest               = "org.scalatest"          %% "scalatest"                      % Version.scalaTest
  val akkaTestkit             = "com.typesafe.akka"      %% "akka-testkit"                   % Version.akka
}

object Resolver {
}
