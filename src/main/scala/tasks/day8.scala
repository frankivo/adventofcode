package com.github.frankivo
package tasks

object day8 {
  def main(args: Array[String]): Unit = {
    println(s"Number of visible trees: ${visibleTrees.size}")
  }

  private case class Coordinate(x: Int, y: Int)

  private val grid: Seq[(Coordinate, Int)] = {
    util.get("day8.txt")
      .zipWithIndex
      .map(y => (y._2, y._1.zipWithIndex))
      .flatMap(i => i._2.map(j => (Coordinate(j._2, i._1), j._1.getNumericValue)))
  }

  private val gridSize: Int = grid.maxBy(_._1.x)._1.x

  private def visibleTrees: Set[Coordinate] = {
    (0 to gridSize).flatMap(i => {
      val x = visibleTreesRow(i)
      println(x.size)
      x
    }).toSet
  }

  private def visibleTreesRow(y: Int): Set[Coordinate] = {
    val row = grid.filter(_._1.y == y)
    val topFromLeft = row.maxBy(_._2)
    val topFromRight = row.reverse.maxBy(_._2)
    val leftToRight = row.filter(c => c._1.x == 0 || (c._1.x <= topFromLeft._1.x) && c._2 < topFromLeft._2)
    val rightToLeft = row.filter(c => c._1.x == gridSize || (c._1.x >= topFromRight._1.x) && c._2 < topFromRight._2)

    (leftToRight ++ rightToLeft)
      .map(_._1)
      .toSet
  }
}
