package com.github.frankivo
package tasks

import scala.collection.mutable

/**
 * Used this as a reference to speed things up https://youtu.be/bLMj50cpOug
 * Note to self: don't use Set in BFS for explorable options
 */

object day16 {
  def main(args: Array[String]): Unit = {
    println(s"Most pressure to release: ${part1()}")
    println(s"Most pressure with helper elephant: ${part2()}")
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val StartNode: String = "AA"

  private def part1(): Int = findBest(StartNode, 0, 30)

  private val cache: mutable.Map[(String, Int, Int), Int] = scala.collection.mutable.Map[(String, Int, Int), Int]()

  private def part2(): Int = {
    val mask = (1 << allDistances(StartNode).size) - 1
    (0 to (mask / 2.0).floor.toInt)
      .map(i => findBest(StartNode, i, 26) + findBest(StartNode, mask ^ i, 26))
      .max
  }

  private def findBest(valve: String, valveStates: Int, time: Int): Int = {
    if (cache.contains((valve, valveStates, time)))
      return cache(valve, valveStates, time)

    val best = allDistances(valve)
      .keys
      .filterNot(x => (valveStates & bits(x)) == bits(x)) // Don't attempt to open opened valves
      .flatMap(neighbour => {
        val remtime = time - allDistances(valve)(neighbour) - 1 // Distance to neighbour valve and time to open
        Option.when(remtime > 0) { // No point in opening when there is no time left after opening
          findBest(neighbour, valveStates | bits(neighbour), remtime) + input(neighbour).flowRate * remtime // Generate flow
        }
      })
      .maxOption
      .getOrElse(0)

    cache((valve, valveStates, time)) = best
    best
  }

  private def explore(start: String): Map[String, Int] = {
    Seq.unfold(Seq[String](start), Map[String, Int](start -> 0)) {
      (explorable, distances) => {
        Option.when(explorable.nonEmpty) {
          val cur = explorable.head
          val adj = input(cur).tunnels.filterNot(distances.contains)

          (distances, (
            explorable.filterNot(_ == cur) ++ adj,
            distances ++ adj.map(_ -> (distances(cur) + 1))
          ))
        }
      }
    }.last
  }

  private val input: Map[String, Valve] = {
    util.get(16)
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

  private val bits: Map[String, Int] = allDistances(StartNode).keys.zipWithIndex.toMap.map(i => (i._1, 1 << i._2))
}
