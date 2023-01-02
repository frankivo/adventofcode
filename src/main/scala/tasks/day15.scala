package com.github.frankivo
package tasks

/**
 * This video helped me trough part 2: https://www.youtube.com/watch?v=pV5nNyjMdFA
 */

object day15 {
  def main(args: Array[String]): Unit = {
    println(s"No beacon at positions: $part1")
    println(s"Beacon tuning frequency: $part2")
  }

  private val scanLine: Int = if (sys.env.contains("DEMO")) 10 else 2_000_000
  private val scanRange: Int = if (sys.env.contains("DEMO")) 20 else 4_000_000

  private def part1: Long = input.flatMap(_.emptyZone).count(_.y == scanLine)

  private def part2: Long = {
    val sensors = input.map(i => (i.sensor.x, i.sensor.y, i.distance))
    val stressBeacon = sensors.flatMap(s => {
      (0 to s._3 + 2).flatMap(dx => {
        val dy = s._3 + 1 - dx
        Seq(
          (s._1 + dx, s._2 + dy),
          (s._1 + dx, s._2 - dy),
          (s._1 - dx, s._2 + dy),
          (s._1 - dx, s._2 - dy),
        )
          .find(xy => {
            (xy._1 >= 0 && xy._1 <= scanRange && xy._2 >= 0 && xy._2 <= scanRange) &&
              sensors.forall(s => manhattan(xy._1, xy._2, s._1, s._2) > s._3)
          })
      })
    }).head

    (400_000_000L * stressBeacon._1) + stressBeacon._2
  }

  private val empty: String = "#"

  private def manhattan(x1: Int, y1: Int, x2: Int, y2: Int): Int = (x1 - x2).abs + (y1 - y2).abs

  private case class sensor(sensor: coordinate, beacon: coordinate) {
    def distance: Int = manhattan(sensor.x, sensor.y, beacon.x, beacon.y)

    def emptyZone: Set[coordinate] = {
      (sensor.y - distance to sensor.y + distance).filter(_ == scanLine).flatMap(y => {
        (sensor.x - distance to sensor.x + distance).map(x => {
          coordinate(x, y, empty)
        })
          .filter(p => manhattan(sensor.x, sensor.y, p.x, p.y) <= distance)
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
