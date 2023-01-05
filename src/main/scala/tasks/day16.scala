package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    println(s"Most pressure to release: ${part1()}")
    println(s"Most pressure with helper elephant: ${part2()}")
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val StartNode: String = "AA"

  private def part1(): Int = {
    findBest(StartNode, 0, 30)
  }

  private def part2(): Int = {
    val b = (1 << allDistances(StartNode).size) - 1
    (0 until  (b  / 2.0).toInt)
      .map(s => findBest(StartNode, s, 26) + findBest(StartNode, b ^ s, 26))
      .max
  }

  private def findBest(valve: String, valveStates: Int, time: Int): Int = {
    allDistances(valve)
      .keys
      .filterNot(x => {
        val bit = 1 << indices(x)
        (valveStates & bit) == bit
      }) // Don't attempt to open opened valves
      .flatMap(neighbour => {
        val remtime = time - allDistances(valve)(neighbour) - 1 // Distance to neighbour valve and time to open
        Option.when(remtime > 0) { // No point in opening when there is no time left after opening
          findBest(neighbour, valveStates | (1 << indices(neighbour)), remtime) + input(neighbour).flowRate * remtime // Generate flow
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
      .filter(v => v._1 == StartNode || v._2.flowRate > 0)
      .keys
      .map(k => (k, explore(k).filterNot(v => input(v._1).flowRate == 0).filterNot(_._1 == k)))
      .toMap
  }

  private val indices = allDistances(StartNode).keys.zipWithIndex.toMap
}
