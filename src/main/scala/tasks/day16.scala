package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    println(part1())
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val StartNode: String = "AA"

  private def part1(): Int = {
    allDistances.foreach(println)
    findBest(StartNode, allDistances(StartNode).keys.map(v => (v, input(v).flowRate == 0)).toMap, 30, 0)
  }

  private def findBest(node: String, valveStates: Map[String, Boolean], timeLeft: Int, bestScore: Int): Int = {
    val newState = valveStates.filterNot(_._1 == node) + (node -> true)
    val dist = allDistances(node)

    val cfr = currentFlowrate(valveStates) * timeLeft

    val bestOption = valveStates
      .filterNot(_._2) // Already open
      .filterNot(vs => dist(vs._1) >= timeLeft) // Unreachable within remaining time
      .map(vs => {
        currentFlowrate(valveStates) + findBest(vs._1, newState, timeLeft - (dist(vs._1) + 1), bestScore)
      })
      .maxOption.getOrElse(-1)

    Seq(bestScore, cfr, bestOption).max
  }

  private def currentFlowrate(valveStates: Map[String, Boolean]): Int = {
    valveStates.filter(_._2).map(vs => input(vs._1).flowRate).sum
  }

  private def explore(start: String): Map[String, Int] = {
    Seq.unfold(Set[String](start), Map[String, Int](start -> 0)) {
      (explorable, distances) => {
        Option.when(explorable.nonEmpty) {
          val cur = explorable.head
          val adj = input(cur).tunnels
          (distances, (
            explorable.filterNot(_ == cur) ++ adj.filterNot(distances.contains),
            distances ++ adj.filterNot(distances.contains).map(_ -> (distances(cur) + 1))
          ))
        }
      }
    }.last
  }

  private val input: Map[String, Valve] = {
    util.get("day16.txt")
      .map("""([A-Z]{2}|[0-9]+)""".r.findAllIn)
      .map(_.matchData.map(_.matched).toSeq)
      .map(m => (m.head, Valve(m(1).toInt, m.drop(2).toSet)))
      .toMap
  }

  private val allDistances: Map[String, Map[String, Int]] = {
    input
      .filter(v => v._1 == "AA" || v._2.flowRate > 0)
      .keys
      .map(k => {
        (k,
          explore(k)
            .filterNot(v => input(v._1).flowRate == 0)
          //            .filterNot(_._1 == k)
        )
      }).toMap
  }
}
