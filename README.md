# Graph Analysis with spark Graphx
 
The project consists of 4 scala files:
1. GraphXSample - This is the main class
2. GenerateGraph - This class will generate the resultant graph using edges and vertices passed by the main class,on which we have to apply different algorithms 
3. AlgorithmSelection - This class contains a menu driven approach to allow user to select one of the multiple graph algorithms which can be applied on the graph. 
4. GraphXAlgorithm - This class contains implementation of algorithms like PageRank, Degree of separation and others provided by GraphX.

## Table of contents  
1. [Getting Started](#Getting-Started) 
2. [How to Run](#How-to-Run) 
3. [How to Use](#How-to-Use) 

  
## Getting Started  
#### Minimum requirements  
To run the SDK you will need  **Java 1.8+, Scala 2.11.12, spark-core 2.4.3, spark-graphx 2.4.4 **.
#### Installation  
The way to use this project is to clone it from github and build it using sbt.

## How to Run 
1. ```sbt clean compile```
2. ```sbt run```

## How to Use
1. Enter the option number for the graph algorithm to apply
2. Output should be visible on the console.

#### Anyone can use these algorithm to analyse data accordingly for instance find most influenced person in the graph using page Rank Algorithm, most connected user in social graph based on graph degrees and so on.
