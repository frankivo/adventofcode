package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    part1()
  }

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private def part1(): Unit = {
    allDistances.foreach(println)
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
    input.keys.map(k => {
      (k,
        explore(k)
          .filterNot(v => input(v._1).flowRate == 0)
          .filterNot(_._1 == k)
      )
    }).toMap
  }
}
