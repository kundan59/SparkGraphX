name := "GraphXSample"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion: String = "2.4.3"

mainClass in compile := Some("com.knoldus.graphxSample.GraphXSample")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-graphx" % "2.4.4"
)
resolvers += Resolver.mavenLocal