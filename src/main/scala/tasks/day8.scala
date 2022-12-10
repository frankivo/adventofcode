package com.github.frankivo
package tasks

object day8 {
  def main(args: Array[String]): Unit = {
    println(s"Number of visible trees: ${visibleTrees.size}")
    println(s"Best scenic score: $bestScenicScore")
  }
  
  private val grid: Seq[(coordinate, Int)] = {
    util.get("day8.txt")
      .zipWithIndex
      .map(y => (y._2, y._1.zipWithIndex))
      .flatMap(i => i._2.map(j => (coordinate(j._2, i._1), j._1.getNumericValue)))
  }

  private val gridSize: Int = grid.maxBy(_._1.x)._1.x

  private def visibleTrees: Set[coordinate] =
    (0 to gridSize).flatMap(i => visibleTrees(i, true) ++ visibleTrees(i, false)).toSet

  private def visibleTrees(index: Int, horizontal: Boolean): Set[coordinate] = {
    val trees = if (horizontal) grid.filter(_._1.x == index) else grid.filter(_._1.y == index)
    val oneWay = visibleTrees(trees, 0, horizontal)
    val reverse = visibleTrees(trees.reverse, gridSize, horizontal)
    oneWay ++ reverse
  }

  private def visibleTrees(trees: Seq[(coordinate, Int)], edge: Int, horizontal: Boolean): Set[coordinate] = {
    trees
      .foldLeft((Seq[coordinate](), 0)) {
        (result, current) => {
          if (
            ((horizontal && current._1.x == edge) || (!horizontal && current._1.y == edge))
              || (current._2 > result._2)
          )
            (result._1 :+ current._1, current._2)
          else result
        }
      }
      ._1.toSet
  }

  private def bestScenicScore: Int = {
    grid.foldLeft(0) {
      (best, tree) => {
        val score = lookDown(tree) * lookUp(tree) * lookLeft(tree) * lookRight(tree)
        Seq(score, best).max
      }
    }
  }

  private def lookRight(tree: (coordinate, Int)): Int = {
    grid
      .filter(_._1.y == tree._1.y) // All trees on col
      .filter(_._1.x > tree._1.x) // All trees on the right
      .find(_._2 >= tree._2)
      .map(_._1.x)
      .getOrElse(gridSize) - tree._1.x
  }

  private def lookLeft(tree: (coordinate, Int)): Int = {
    tree._1.x - grid
      .filter(_._1.y == tree._1.y) // All trees on col
      .filter(_._1.x < tree._1.x) // All trees on the left
      .findLast(_._2 >= tree._2)
      .map(_._1.x)
      .getOrElse(0)
  }

  private def lookDown(tree: (coordinate, Int)): Int = {
    grid
      .filter(_._1.x == tree._1.x)
      .filter(_._1.y > tree._1.y)
      .find(_._2 >= tree._2)
      .map(_._1.y)
      .getOrElse(gridSize) - tree._1.y
  }

  private def lookUp(tree: (coordinate, Int)): Int = {
    tree._1.y -
      grid
        .filter(_._1.x == tree._1.x)
        .filter(_._1.y < tree._1.y)
        .findLast(_._2 >= tree._2)
        .map(_._1.y)
        .getOrElse(0)
  }
}
