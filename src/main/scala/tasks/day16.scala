package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    println(part1())
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val StartNode: String = "AA"

  private def part1(): Int = {
    allDistances.foreach(x =>
      val s = x._2.toSeq.sortBy(v => (v._2, v._1))
      println(s"${x._1} --> $s")
    )
    findBest(StartNode, allDistances(StartNode).keys.map(v => (v, input(v).flowRate == 0)).toMap, 30)
  }

  private def findBest(valve: String, valveStates: Map[String, Boolean], time: Int): Int = {
    var maxval = 0

    allDistances(valve).keys.foreach(neighbour => {
      val remtime = time - allDistances(valve)(neighbour) - 1
      if (remtime > 0 && !valveStates(neighbour)) {
        val newState = valveStates.filterNot(_._1 == neighbour) + (neighbour -> true)
        val score = findBest(neighbour, newState, remtime) + input(neighbour).flowRate * remtime
        maxval = Seq(maxval, score).max
      }
    })

    maxval
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
      .map(k => (k, explore(k).filterNot(v => input(v._1).flowRate == 0).filterNot(_._1 == k)))
      .toMap
  }
}
