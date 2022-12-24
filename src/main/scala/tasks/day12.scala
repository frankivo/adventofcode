package com.github.frankivo
package tasks

object day12 {
  def main(args: Array[String]): Unit = {
    findEnd()
  }

  private def findEnd(): Unit = {
    findEnd(start, Seq(start))
  }

  private def findEnd(node: coordinate, visited: Seq[coordinate]): Unit = {
    if (node == end) {
      println(visited.size)
      return
    }

    node
      .validAdjacent
      .filterNot(visited.contains)
      .foreach(o => findEnd(o, visited :+ o))
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
