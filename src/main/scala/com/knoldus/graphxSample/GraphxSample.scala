package com.knoldus.graphxSample

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

case class Vertices(id: Long, props: (String, Int))

case class Edges(srcId: Long, dstId: Long, relation: String)

object GraphXSample {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    //create spark context
    val conf: SparkConf = new SparkConf().setAppName("SparkGraphXExample").setMaster("local")
    val sc = new SparkContext(conf)

    //create vertex and edges RDD from the csv file of vertexData and edgeData respectively.
    val textVerticesRDD: RDD[String] = sc.textFile("src/main/resources/GraphData/vertexDataFile.csv")
    val textEdgesRDD: RDD[String] = sc.textFile("src/main/resources/GraphData/edgeDataFile.csv")

    //instantiate GenerateGraph class and create resultant graph from vertices and edges.
    val graph = new GenerateGraph()
    val generatedGraph = graph.createGraph(textVerticesRDD, textEdgesRDD)

    //instantiate AlgorithmSelection class to execute algorithms over graph.
    val algorithmSelection = new AlgorithmSelection()
    algorithmSelection.selectAlgorithm(generatedGraph)
  }
}
