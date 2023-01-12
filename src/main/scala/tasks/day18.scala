package com.github.frankivo
package tasks

object day18 {
  def main(args: Array[String]): Unit = {
    println(s"Number of sides that aren't connected: ${part1()}")
  }

  private def part1() : Int = sides - connected

  private def sides : Int = input.size * 6

  private def connected: Int = {
    input.foldLeft(0) {
      (sum, cur) => {
        sum + cur.adjacent.count(input.contains)
      }
    }
  }

  extension (c: coordinate) {
    private def adjacent: Set[coordinate] = {
      Set(
        coordinate(c.x - 1, c.y, c.z),
        coordinate(c.x + 1, c.y, c.z),
        coordinate(c.x, c.y - 1, c.z),
        coordinate(c.x, c.y + 1, c.z),
        coordinate(c.x, c.y, c.z - 1),
        coordinate(c.x, c.y, c.z + 1),
      )
    }
  }

  def input: Set[coordinate] = {
    util.get("day18.txt")
      .mkString(",")
      .split(",").toSeq
      .map(_.toInt)
      .grouped(3)
      .map(l => coordinate(l(0), l(1), l(2)))
      .toSet
  }
}