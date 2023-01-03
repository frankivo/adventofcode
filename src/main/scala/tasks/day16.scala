package com.github.frankivo
package tasks

object day16 {
  def main(args: Array[String]): Unit = {
    //    explore("AA").toSeq.sortBy(_._2).foreach(println)
    part1()
  }

  def part1(): Unit = {
    var valves = input.keySet.map(v => (v, input(v).flowRate == 0))
    var pos = "AA"

    var minute = 1
    var released = 0

    while (minute <= 30) {
      val routes = explore(pos)
      val options = valves.filterNot(_._2).map(_._1).map(v => (v, routes(v), input(v).flowRate))
      val togo = options.toSeq.minBy(o => (o._2, -o._3))

      println(s"move to ${togo._1}")

      released += pressure(valves)

      if (routes(togo._1) == 1) {
        pos = togo._1
        valves = open(togo._1, valves)
      }
      else {
        println(s"$pos --> $togo")
      }

      minute += 1
    }
    println(released)
  }

  private def open(v: String, valves: Set[(String, Boolean)]): Set[(String, Boolean)] = {
    println(s"open $v")
    valves.filterNot(_._1 == v) + (v -> true)
  }

  private def pressure(valves: Set[(String, Boolean)]): Int =
    valves.filter(_._2).map(v => input(v._1).flowRate).sum

  // TODO: track steps
  // TODO: track gain (remaining minutes * flowrate)
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

  private case class Valve(flowRate: Int, tunnels: Set[String])

  private val input: Map[String, Valve] = {
    util.get("day16.txt")
      .map("""([A-Z]{2}|[0-9]+)""".r.findAllIn)
      .map(_.matchData.map(_.matched).toSeq)
      .map(m => (m.head, Valve(m(1).toInt, m.drop(2).toSet)))
      .toMap
  }
}
