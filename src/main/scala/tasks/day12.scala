package com.github.frankivo
package tasks

object day12 {
  def main(args: Array[String]): Unit = {
    println(findBest())
    println(findBest().size)
  }

  private def findBest(): Set[coordinate] = {
    val all = findEnd(start, Set(start))
    println(all.size)

    Set()
  }

  private def findEnd(node: coordinate, visited: Set[coordinate]): Seq[Set[coordinate]] = {
    val options = node.validAdjacent.filterNot(visited.contains)
    if (options.isEmpty)
      Seq(visited)
    else if (options.contains(end))
      Seq(visited + end)
    else
      val x = options.map(findEnd(_, visited + node))
      x.flatten
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

  val scores: Map[Char, Int] = ('a' to 'z')
    .zip(LazyList.from(1))
    .toMap
    + ('S' -> 0)
    + ('E' -> 27)

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
      adjacent.filter(a => numVal <= a.numVal || numVal - a.numVal == 1)

    private def numVal: Int = scores(coord.char)
  }
}
