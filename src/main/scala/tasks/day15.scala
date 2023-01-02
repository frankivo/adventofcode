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
    val findY = input
      .foldLeft(input) {
        (res, i) => {
          res.filter(input
            .filterNot(x => i.minX > x.minX && i.minX < x.maxX)
            .filterNot(x => i.maxX > x.minX && i.maxX < x.maxX)
          )
        }
      }

    val findX = input
      .foldLeft(input) {
        (res, i) => {
          res.filter(input
            .filterNot(y => i.minY > y.minY && i.minY < y.maxY)
            .filterNot(y => i.maxY > y.minY && i.maxY < y.maxY)
          )
        }
      }

    (findX.head.sensor.x * 4_000_000) + findY.head.sensor.y
  }

  private val empty: String = "#"

  extension (coord: coordinate) {
    def manhatten(other: coordinate): Int = (coord.x - other.x).abs + (coord.y - other.y).abs
  }

  private case class lineblock(line: Int, left: Int, right: Int)

  private case class sensor(sensor: coordinate, beacon: coordinate) {
    private def distance: Int = sensor.manhatten(beacon)

    def minX = Seq(0, sensor.x - distance).max + 1

    def maxX = Seq(scanRange, sensor.x + distance).min + 1

    def minY = Seq(0, sensor.y - distance).max + 1

    def maxY = Seq(scanRange, sensor.y + distance).min + 1

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
