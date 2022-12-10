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
        val head = moveHead(his.head, move)
        val tail = moveTail(his.tail, head, move)

        println(s"head: $head tail: $tail")

        ropestate(head, tail, his.tailPositions + tail)
      }
    }
    println(s"H pos: $pos")
  }

  private def moveHead(old: coordinate, move: Char): coordinate = {
    move match
      case 'R' => coordinate(old.x + 1, old.y)
      case 'L' => coordinate(old.x - 1, old.y)
      case 'U' => coordinate(old.x, old.y + 1)
      case 'D' => coordinate(old.x, old.y - 1)
  }

  private def moveTail(old: coordinate, head: coordinate, move: Char): coordinate = {
    if (old.distance(head) <= 1)
      old
    else
      moveHead(old, move)
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
