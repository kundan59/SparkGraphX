package com.knoldus.graphxSample

import org.apache.spark.graphx.Graph

class AlgorithmSelection {

  def selectAlgorithm(generatedGraph: Graph[(String, Int), String]) {

    println("..................................................................")
    println("Press 1 to get inDegree and outDegree for each vertices")
    println("Press 2 to find degree of separation for the given vertex")
    println("Press 3 to find degree of separation between the given two vertices")
    println("Press 4 to get page Rank for each vertices using pregel interface")
    println("Press 5 to get page Rank for each vertices using Graph interface")
    println("Press 6 to Compute the connected component membership of each vertex")
    println("Press 7 to find Number of triangles passes through each vertex")
    println("....................................................................")

    println("enter Number for which algorithm you want to execute")
    val algorithmNumber = scala.io.StdIn.readInt()

    // Instantiate GraphXAlgorithm  and choose which algorithm wants to apply on graph
    val algorithms = new GraphXAlgorithm()

    algorithmNumber match {

      case 1 => algorithms.inDegreeAndOutDegree(generatedGraph)

      case 2 => {
        println("enter vertexId for which finding degree of separation")
        val vertexId = scala.io.StdIn.readInt()
        algorithms.degreeOfSeparationSingleNode(generatedGraph, vertexId)
      }

      case 3 => {
        println("enter First vertexId for which finding degree of separation")
        val firstVertexId = scala.io.StdIn.readInt()
        println("enter Second vertexId to which finding degree of separation")
        val secondVertexId = scala.io.StdIn.readInt()
        algorithms.degreeOfSeparationBetweenTwoNode(generatedGraph, firstVertexId, secondVertexId)
      }

      case 4 => algorithms.pageRankPregelDynamic(generatedGraph)

      case 5 => algorithms.pageRankIterative(generatedGraph)

      case 6 => algorithms.connectedComponent(generatedGraph)

      case 7 => algorithms.triangleCount(generatedGraph)

      case _ => println("No such algorithm available for this number please enter only provided numbers ")
    }
  }
}
