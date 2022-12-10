package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    println(moves)
    doMoves()
  }

  case class ropestate(head: coordinate, tail: coordinate, tailPositions: Set[coordinate])

  def doMoves(): Unit = {
    val startPos = coordinate(0, 0)
    val start = ropestate(startPos, startPos, Set(startPos))

    val pos = moves.foldLeft(start) {
      (his, move) => {
        val head = move match {
          case 'R' => coordinate(his.head.x + 1, his.head.y)
          case 'L' => coordinate(his.head.x - 1, his.head.y)
          case 'U' => coordinate(his.head.x, his.head.y + 1)
          case 'D' => coordinate(his.head.x, his.head.y - 1)
        }
        ropestate(head, his.tail, his.tailPositions)
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
