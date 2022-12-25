package com.github.frankivo
package tasks

/**
 * This video helped me a lot: https://www.youtube.com/watch?v=xcIUM003HS0
 */

object day12 {
  def main(args: Array[String]): Unit = {
    println(s"Distance from S: $part1")
    println(s"Best distance: $part2")
  }

  private def part1: Int = explore(start)

  private def part2: Int = {
    input
      .filter(i => Seq('S', 'a').contains(i.char))
      .foldLeft(Int.MaxValue) {
        (best, cur) => Seq(explore(cur), best).min
      }
  }

  def explore(startPos: coordinate): Int = {
    val distances = Map[coordinate, Int](startPos -> 0)
    val explorable = Seq[coordinate](startPos)

    Seq.unfold(distances, explorable) {
      (d, e) => {
        if (e.isEmpty) None
        else {
          val cur = e.head
          val dist = d(cur) + 1

          val newD = d ++ cur.validAdjacent.filterNot(d.contains).map(adj => adj -> dist)
          val newE = e.filterNot(_.eq(cur)) ++ cur.validAdjacent.filterNot(d.contains)
          val endDistance = newD.get(end)

          Some(endDistance, (newD, newE))
        }
      }
    }.flatten
      .minOption.getOrElse(Int.MaxValue)
  }

  private val input: Seq[coordinate] = {
    util.get("day12.txt")
      .zipWithIndex.flatMap(y =>
      y._1.zipWithIndex
        .map(x => coordinate(x = x._2, y = y._2, name = s"${x._1}"))
    )
  }

  private val start: coordinate = input.find(_.char.equals('S')).get

  private val end: coordinate = input.find(_.char.equals('E')).get

  private val heights: Map[Char, Int] =
    ('a' to 'z')
      .zip(LazyList.from(1))
      .toMap
      + ('S' -> 1)
      + ('E' -> 26)

  extension (coord: coordinate) {
    private def char: Char = coord.name.head

    private def adjacent: Seq[coordinate] =
      Seq(
        (coord.x - 1, coord.y), // Left
        (coord.x + 1, coord.y), // Right
        (coord.x, coord.y - 1), // Up
        (coord.x, coord.y + 1), // Down
      ).flatMap(c => input.find(i => i.x == c._1 && i.y == c._2))

    private def validAdjacent: Seq[coordinate] =
      adjacent.filter(a => a.height <= height || a.height - height == 1)

    private def height: Int = heights(coord.char)
  }
}
