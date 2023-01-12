package com.github.frankivo
package tasks

object day18 {
  def main(args: Array[String]): Unit = {
    println(s"Number of sides that aren't connected: ${part1()}")
    println(s"Number of exterior exposed sides: ${part2()}")
  }

  private def part1(): Int = sides - connected(input)

  private def part2(): Int = {
    var explorable = Seq[coordinate](box._1)
    var seen = Set.empty[coordinate]
    var count = 0

    while (explorable.nonEmpty) {
      val cur = explorable.head
      explorable = explorable.drop(1)

      if (input.contains(cur))
        count += 1
      else if (!seen.contains(cur)) {
        seen = seen + cur
        explorable = explorable ++ cur.adjacent.filter(reachable)
      }
    }

    count
  }

  private def sides: Int = input.size * 6

  private def connected(field: Set[coordinate]): Int = {
    field.foldLeft(0) {
      (sum, cur) => sum + cur.adjacent.count(field.contains)
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

    private def reachable: Boolean = {
      util.clamp(c.x, box._1.x, box._2.x) == c.x &&
        util.clamp(c.y, box._1.y, box._2.y) == c.y &&
        util.clamp(c.z, box._1.z, box._2.z) == c.z
    }
  }

  def input: Set[coordinate] = {
    util.get("day18.txt")
      .mkString(",")
      .split(",").toSeq
      .map(_.toInt)
      .grouped(3)
      .map(coordinate.apply)
      .toSet
  }

  private val box: (coordinate, coordinate) = (
    coordinate(input.minBy(_.x).x - 2, input.minBy(_.y).y - 2, input.minBy(_.z).z - 2),
    coordinate(input.maxBy(_.x).x + 2, input.maxBy(_.y).y + 2, input.maxBy(_.z).z + 2)
  )
}