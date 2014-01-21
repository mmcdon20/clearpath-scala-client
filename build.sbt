name := "clearpath scala client"

version := "0.1"

resolvers += "spray repo" at "http://repo.spray.io/"

libraryDependencies ++= {
  val sprayVersion = "1.1-M8"
  val akkaVersion  = "2.1.1"
  Seq(
    "io.spray"          %% "spray-json"      % "1.2.5",
    "io.spray"          %  "spray-client"    % sprayVersion,
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.0.7",
    "org.scala-lang"    %  "scala-reflect"   % "2.10.2",
    "org.scalatest"     %% "scalatest"       % "1.9.1" % "test",
    "junit"             %  "junit"           % "4.10" % "test"
  )
}

scalaVersion := "2.10.0"
