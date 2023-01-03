package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    input.foreach(println)
  }

  private case class Valve(name: String, flowRate: Int, tunnels: Set[String])

  private val input: Set[Valve] = {
    util.get("day16.txt")
      .map("""([A-Z]{2}|[0-9]+)""".r.findAllIn)
      .map(_.matchData.map(_.matched).toSeq)
      .map(m => Valve(m.head, m(1).toInt, m.drop(2).toSet))
      .toSet
  }
}
