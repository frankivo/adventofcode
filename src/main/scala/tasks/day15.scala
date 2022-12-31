package com.github.frankivo
package tasks

object day15 {
  def main(args: Array[String]): Unit = {
    println(s"Input size: ${input.size}")
    println(s"No beacon at positions: $part1")
  }

  private def emptyZones: Set[coordinate] = input.flatMap(_.emptyZone)

  private def part1: Long = {
    val line = if (sys.env.contains("DEMO")) 10 else 2000000
    emptyZones.count(_.y == line)
  }

  private val empty: String = "#"

  extension (coord: coordinate) {
    def manhatten(other: coordinate): Int = (coord.x - other.x).abs + (coord.y - other.y).abs
  }

  private case class sensor(sensor: coordinate, beacon: coordinate) {
    private def distance: Int = sensor.manhatten(beacon)

    def emptyZone: Set[coordinate] = {
      (sensor.y - distance to sensor.y + distance).flatMap(y => {
        (sensor.x - distance to sensor.x + distance).map(x => {
          coordinate(x, y, empty)
        })
          .filter(_.manhatten(sensor) <= distance)
      })
        .filterNot(e => e.equals(beacon))
        .toSet
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
