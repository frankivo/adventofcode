package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    println(input.size)+
    println(explore().size)
  }

  def explore(): Map[String, Int] = {
    Seq.unfold(Set[String]("AA"), Map[String, Int]("AA" -> 0)) {
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

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val input: Map[String, Valve] = {
    util.get("day16.txt")
      .map("""([A-Z]{2}|[0-9]+)""".r.findAllIn)
      .map(_.matchData.map(_.matched).toSeq)
      .map(m => (m.head, Valve(m(1).toInt, m.drop(2).toSet)))
      .toMap
  }
}
