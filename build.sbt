resolvers += "spray repo" at "http://repo.spray.io/"

libraryDependencies ++= {
  val sprayVersion = "1.1-M8"
  val akkaVersion  = "2.1.1"
  Seq(
    "io.spray"          %% "spray-json"      % "1.2.5",
    "io.spray"          %  "spray-client"    % sprayVersion,
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.0.7"
  )
}

scalaVersion := "2.10.0"
