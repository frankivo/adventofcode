package com.github.frankivo
package tasks

object day8 {
  def main(args: Array[String]): Unit = {
    println(grid)
    visibleTrees
  }

  private case class Coordinate(x: Int, y: Int)

  private val grid: Seq[(Coordinate, Int)] = {
    util.get("day8.txt")
      .zipWithIndex
      .map(y => (y._2, y._1.zipWithIndex))
      .flatMap(i => i._2.map(j => (Coordinate(j._2, i._1), j._1.getNumericValue)))
  }

  private val gridSize: Int = grid.length / 4 - 1

  private def visibleTrees: Seq[Coordinate] = {
    println(visibleTreesRow(1).toSeq.sortBy(_.x))
    Seq()
  }

  private def visibleTreesRow(y: Int): Set[Coordinate] = {
    val row = grid.filter(_._1.y == y)
    val topFromLeft = row.maxBy(_._2)._1.x
    val topFromRight = row.reverse.maxBy(_._2)._1.x
    val leftToRight = row.filter(_._1.x <= topFromLeft)
    val rightToLeft = row.filter(_._1.x >= topFromRight)

    (leftToRight ++ rightToLeft)
      .map(_._1)
      .toSet
  }
}
