package com.github.frankivo
package tasks


object day15 {
  def main(args: Array[String]): Unit = {
    //    println(s"No beacon at positions: $part1")
    println(s"No beacon at positions: $part2")
  }

  private val scanLine: Int = if (sys.env.contains("DEMO")) 10 else 2_000_000
  private val scanRange: Int = if (sys.env.contains("DEMO")) 20 else 4_000_000

  private def part1: Long = input.flatMap(_.emptyZone).count(_.y == scanLine)

  private def part2: Long = {
    val emptyZone = input.map(_.stressSignal).reduce(_ ++ _)
    println(emptyZone.size)

    val range = (0 to scanRange).toSet
    val a = range.flatMap(l => {
      val b = emptyZone.filter(_.line == l)
      val c = b.foldLeft(range) {
        (r, cur) => r.filterNot(i => i >= cur.left && i <= cur.right)
      }
      Option.when(c.nonEmpty)(coordinate(c.head, l))
    })
    (a.head.x * 4000000) + a.head.y
  }

  private val empty: String = "#"

  extension (coord: coordinate) {
    def manhatten(other: coordinate): Int = (coord.x - other.x).abs + (coord.y - other.y).abs
  }

  private case class lineblock(line: Int, left: Int, right: Int)

  private case class sensor(sensor: coordinate, beacon: coordinate) {
    private def distance: Int = sensor.manhatten(beacon)

    def emptyZone: Set[coordinate] = {
      (sensor.y - distance to sensor.y + distance).filter(_ == scanLine).flatMap(y => {
        (sensor.x - distance to sensor.x + distance).map(x => {
          coordinate(x, y, empty)
        })
          .filter(_.manhatten(sensor) <= distance)
      })
        .filterNot(e => e.equals(beacon))
        .toSet
    }

    def stressSignal: Set[lineblock] = {
      (Seq(0, sensor.y - distance).max to Seq(scanRange, sensor.y + distance).min)
        .map(y => {
          val leftover = distance - (sensor.y - y).abs
          lineblock(y, Seq(0, sensor.x - leftover).max, Seq(scanRange, sensor.x + leftover).min)
        }).toSet
    }
  }

  private val input: Set[sensor] = {
    util.get("day15.txt")
      .flatMap("""([\-0-9]+)""".r.findAllMatchIn)
      .map(_.matched.toInt)
      .grouped(4)
      .map(g => {
        sensor(
          coordinate(g(0), g(1), "S"),
          coordinate(g(2), g(3), "B")
        )
      })
      .toSet
  }
}
