package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    println(moves)
    doMoves()
  }

  def doMoves(): Unit = {
    val pos = moves.foldLeft(coordinate(0, 0)) {
      (pos, move) => {
        move match {
          case 'R' => coordinate(pos.x + 1, pos.y)
          case 'L' => coordinate(pos.x - 1, pos.y)
          case 'U' => coordinate(pos.x, pos.y + 1)
          case 'D' => coordinate(pos.x, pos.y - 1)
        }
      }
    }
    println(s"H pos: $pos")
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
