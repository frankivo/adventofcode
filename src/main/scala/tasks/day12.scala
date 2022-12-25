package com.github.frankivo
package tasks

/**
 * Not immutable, yet?
 * This video helped me a lot: https://www.youtube.com/watch?v=xcIUM003HS0
 */

object day12 {
  def main(args: Array[String]): Unit = {
    //    println(s"Distance from S: $part1")
    //    println(s"Best distance: $part2")

    explore()
  }

  private def part1: Int = BFS().explore(start)

  private def part2: Int = {
    input
      .filter(i => Seq('S', 'a').contains(i.char))
      .foldLeft(Int.MaxValue) {
        (best, cur) => Seq(BFS().explore(cur), best).min
      }
  }

  def explore(): Unit = {
    val distances = Map[coordinate, Int](start -> 0)
    val explorable = Seq[coordinate](start)

    val out = Seq.unfold(distances, explorable) {
      (d, e) => {
        if (e.isEmpty) None
        else {
          val cur = explorable.head
          val dist = distances(cur) + 1

          val newD = d ++ cur
            .validAdjacent
            .filterNot(distances.contains)
            .map(adj => adj -> dist)

          val newE = e.filterNot(_.eq(cur)) ++ cur.validAdjacent
            .filterNot(newD.contains)

          println(1)

          Some(newD.minBy(_._2)._2, (newD, newE))
        }
      }
    }

    println("Out: " + out)
  }

  class BFS() {
    private var distances = Map[coordinate, Int]()
    private var explorable = Seq[coordinate]()

    def explore(startNode: coordinate): Int = {
      distances = distances + (startNode -> 0)
      explorable = explorable.add(startNode)
      explore()

      distances.getOrElse(end, Int.MaxValue)
    }

    private def explore(): Unit = {
      while (explorable.nonEmpty) {
        val cur = explorable.head
        val dist = distances(cur) + 1
        cur
          .validAdjacent
          .filterNot(distances.contains)
          .foreach(adj => {
            distances = distances + (adj -> dist)
            explorable = explorable.add(adj)
          })
        explorable = explorable.rem(cur)
      }
    }
  }

  extension (lst: Seq[coordinate]) { // Hack Seq into a Queue
    def add(item: coordinate): Seq[coordinate] = lst :+ item

    def rem(item: coordinate): Seq[coordinate] = lst.filterNot(_.eq(item))
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
