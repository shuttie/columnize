name := "columnize"

version := "0.1-dev"

scalaVersion := "2.11.7"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.0"

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.4.0"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.11"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"

libraryDependencies += "joda-time" % "joda-time" % "2.8.2"

libraryDependencies += "commons-codec" % "commons-codec" % "1.10"

libraryDependencies += "org.parboiled" %% "parboiled" % "2.1.0"