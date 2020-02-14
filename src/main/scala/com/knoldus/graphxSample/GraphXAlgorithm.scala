package com.knoldus.graphxSample

import org.apache.spark.graphx.{Graph, VertexId}

class GraphXAlgorithm {

  //instantiate AlgorithmSelection class to execute algorithms over graph.
  val algorithmSelection = new AlgorithmSelection()

  /**
   * inDegree and outDegree of vertices.
   *
   * @param generatedGraph represent graph.
   * @return inDegree and outDegree of each vertex.
   */
  def inDegreeAndOutDegree(generatedGraph: Graph[(String, Int), String]) {

    generatedGraph.inDegrees.collect().sorted.foreach(vertexId =>
      println("vertex " + vertexId._1 + " has " + vertexId._2 + " inDegree"))

    generatedGraph.outDegrees.collect().sorted.foreach(vertexId =>
      println("vertex " + vertexId._1 + " has " + vertexId._2 + " outDegree"))
    userChoice(generatedGraph)
  }

  /**
   * Degree of separation for the single Node.
   *
   * @param generatedGraph represent graph.
   * @param vertexId       The point of departure in BFS.
   * @return Degree of separation for the given Node.
   */
  def degreeOfSeparationSingleNode(generatedGraph: Graph[(String, Int), String],
                                   vertexId: VertexId): Unit = {

    getBFS(generatedGraph, vertexId).vertices
      .foreach(vertexes => println("vertex " + vertexId + " has degree of separation from vertex "
        + vertexes._1 + " is: " + vertexes._2))
    userChoice(generatedGraph)
  }

  /**
   * Degree of separation between two Node.
   *
   * @param generatedGraph represent graph.
   * @param firstNode      VertexId for the first user.
   * @param secondNode     VertexId for the second user.
   * @return Degree of separation for the given Nodes.
   */
  def degreeOfSeparationBetweenTwoNode(generatedGraph: Graph[(String, Int), String],
                                       firstNode: VertexId, secondNode: VertexId): Unit = {

    getBFS(generatedGraph, firstNode)
      .vertices
      .filter { case (vertexId, _) => vertexId == secondNode }
      .collect.map { case (_, degree) => degree } foreach println
    userChoice(generatedGraph)
  }

  /**
   * Represent breadth-first search statement for graph
   * via delegation to Pregel algorithm starting from the edge root
   *
   * @param root The point of departure in BFS
   * @return breadth-first search statement
   */
  private def getBFS(graph: Graph[(String, Int), String], root: VertexId) = {

    val initialGraph = graph.mapVertices((id, _) =>
      if (id == root) 0.0 else Double.PositiveInfinity)

    initialGraph.pregel(Double.PositiveInfinity, maxIterations = 10)(
      (_, attr, msg) => math.min(attr, msg),
      triplet => {
        if (triplet.srcAttr != Double.PositiveInfinity) {
          Iterator((triplet.dstId, triplet.srcAttr + 1))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b)).cache()
  }

  /**
   * Dynamic implementation uses the Pregel interface and runs PageRank until convergence.
   *
   * @param generatedGraph represent resultant graph.
   * @return PageRank, importance of each vertices.
   */
  def pageRankPregelDynamic(generatedGraph: Graph[(String, Int), String]): Unit = {

    generatedGraph.pageRank(tol = 0.0001).vertices.sortByKey() foreach println
    userChoice(generatedGraph)
  }

  /**
   * The standalone Graph interface and runs PageRank for a fixed number of iterations.
   *
   * @param generatedGraph represent resultant graph.
   * @return PageRank, importance of each vertices.
   */
  def pageRankIterative(generatedGraph: Graph[(String, Int), String]): Unit = {

    generatedGraph.staticPageRank(numIter = 20).vertices.sortByKey() foreach println
    userChoice(generatedGraph)
  }

  /**
   * Compute the connected component membership of each vertex,
   * id of component is the lowest vertex id in a certain component.
   *
   * @param generatedGraph represent resultant graph.
   * @return Tuple vertex id - lowest vertex id in a component
   */
  def connectedComponent(generatedGraph: Graph[(String, Int), String]): Unit = {

    generatedGraph.connectedComponents().vertices.sortByKey() foreach println
    userChoice(generatedGraph)
  }

  /**
   * Number of triangles passes through each vertex.
   *
   * @param generatedGraph represent resultant graph.
   * @return Number of triangles passing through each vertex.
   */
  def triangleCount(generatedGraph: Graph[(String, Int), String]): Unit = {

    generatedGraph.triangleCount().vertices.sortByKey().foreach(vertex => println(vertex._2 +
      " Triangles passes through the vertex " + vertex._1))
    userChoice(generatedGraph)
  }

  /**
   * provided choice to user.
   */
  def userChoice(generatedGraph: Graph[(String, Int), String]): Unit = {

    println("Wants to execute other algorithms press(y/n)?  ")
    val yesOrNo = scala.io.StdIn.readChar()
    if (yesOrNo == 'y' || yesOrNo == 'Y') {
      algorithmSelection.selectAlgorithm(generatedGraph)
    } else {
      sys.exit()
    }
  }
}
