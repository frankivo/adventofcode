package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    input.foreach(println)
    explore()
  }

  def explore(): Unit = {
    var distances = Map[String, Int]("AA" -> 0)
    var explorable = Set[String]("AA")

    while (explorable.nonEmpty) {
      val cur = explorable.head
      val dist = distances(cur) + 1

      val adj = input(cur).tunnels

      explorable = explorable.filterNot(_ == cur) ++ adj.filterNot(distances.contains)
      distances = distances ++ adj.filterNot(distances.contains).map(_ -> dist)
    }

    println(distances)

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
