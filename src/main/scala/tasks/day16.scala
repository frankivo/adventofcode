package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    println(part1())
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val StartNode: String = "AA"

  private def part1(): Int = {
    findBest(StartNode, allDistances(StartNode).keys.map(v => (v, input(v).flowRate == 0)).toMap, 30)
  }

  private def findBest(valve: String, valveStates: Map[String, Boolean], time: Int): Int = {
    allDistances(valve)
      .keys
      .filterNot(valveStates) // Don't attempt to open opened valves
      .flatMap(neighbour => {
        val remtime = time - allDistances(valve)(neighbour) - 1 // Distance to neighbour valve and time to open
        Option.when(remtime > 0) { // No point in opening when there is no time left after opening
          val newState = valveStates.filterNot(_._1 == neighbour) + (neighbour -> true) // Open valve
          findBest(neighbour, newState, remtime) + input(neighbour).flowRate * remtime // Generate flow
        }
      })
      .maxOption
      .getOrElse(0)
  }

  private def explore(start: String): Map[String, Int] = {
    Seq.unfold(Set[String](start), Map[String, Int](start -> 0)) {
      (explorable, distances) => {
        Option.when(explorable.nonEmpty) {
          val cur = explorable.head
          val adj = input(cur).tunnels

          (distances, (
            explorable.filterNot(_ == cur) ++ adj.filterNot(distances.contains),
            mergeDistances(distances, adj.map(_ -> (distances(cur) + 1)).toMap)
          ))
        }
      }
    }.last
  }

  private def mergeDistances(old: Map[String, Int], updates: Map[String, Int]): Map[String, Int] = {
    old.map(o => (o._1, Seq(o._2, updates.getOrElse(o._1, Int.MaxValue)).min)) ++
      updates.filterNot(u => old.keys.exists(_ == u._1))
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
      .map(k => (k, explore(k).filterNot(v => input(v._1).flowRate == 0).filterNot(_._1 == k)))
      .toMap
  }
}
