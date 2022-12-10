package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    //    println(s"Amount of unique tail positions: ${amountTailPositions(1)}")
    println(s"Amount of unique tail positions: ${amountTailPositions(9)}")
  }

  case class ropestate(head: coordinate, tail: Seq[coordinate], tailPositions: Set[coordinate])

  private def amountTailPositions(tailSize: Int): Int = {
    val startPos = coordinate(0, 0)
    val tail = (0 to tailSize).map(_ => startPos)

    val start = ropestate(startPos, tail, Set(startPos))

    val history = moves.foldLeft(start) {
      (his, move) => {
        val head = moveHead(his.head, move)
        val x =his.tail.sliding(2, 1) // Needs head
        val tails = x
          .map(t => {
            if (t.length == 1) t.head
            else
              moveTail(t.head, t(1), move)
          }).toSeq


        ropestate(head, tails, his.tailPositions + tails.last)
      }
    }
    history.tailPositions.size
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
    else {
      val tail = moveHead(old, move)
      coordinate(
        if ("UD".contains(move)) head.x else tail.x,
        if ("LR".contains(move)) head.y else tail.y
      )
    }
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
