package com.knoldus.graphxSample

import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD

class GenerateGraph {

  /**
   * Build a social graph from vertex and edges
   *
   * @param textVerticesRDD represent vertices of graph.
   * @param textEdgesRDD    represent edges of graph.
   * @return resultant graph
   */
  def createGraph(textVerticesRDD: RDD[String], textEdgesRDD: RDD[String]): Graph[(String, Int), String] = {

    //create vertices for graph
    val verticesRDD: RDD[Vertices] = textVerticesRDD.map { line =>
      val lineParts = line.split(",")
      Vertices(lineParts(0).toLong, (lineParts(1), lineParts(2).toInt))
    }
    val vertices = verticesRDD.map(vertices => (vertices.id, (vertices.props._1, vertices.props._2)))

    //create Edges for graph
    val edgesRDD: RDD[Edges] = textEdgesRDD.map { line =>
      val lineParts = line.split(",")
      Edges(lineParts(0).toLong, lineParts(1).toLong, lineParts(2))
    }
    val edges = edgesRDD.map(edges => Edge(edges.srcId, edges.dstId, edges.relation))

    //resultant graph
    val graph = Graph(vertices, edges)
    graph
  }
}
