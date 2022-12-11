package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    //    println(s"Amount of unique tail positions: ${amountTailPositions(1)}")
    println(s"Amount of unique tail positions: ${amountTailPositions(9)}")
  }

  case class ropestate(head: coordinate, tail: Seq[coordinate], tailPositions: Set[coordinate])

  private def amountTailPositions(tailSize: Int): Int = {
    val startPos = coordinate(0, 0, "H")
    val tail = (1 to tailSize).map(i => coordinate(0, 0, i.toString))

    val start = ropestate(startPos, tail, Set(startPos))

    val history = moves.foldLeft(start) {
      (his, move) => {
        val head = moveHead(his.head, move)
        val x = (head +: his.tail).sliding(2, 1).toSeq
        val tails = x
          .map(t => {
            if (t.length == 1)
              t.last
            else
              moveTail(t.last, t.head, move)
          })

        ropestate(head, tails, his.tailPositions + tails.last)
      }
    }
    history.tailPositions.size
  }

  private def moveHead(old: coordinate, move: Char): coordinate = {
    move match
      case 'R' => coordinate(old.x + 1, old.y, old.name)
      case 'L' => coordinate(old.x - 1, old.y, old.name)
      case 'U' => coordinate(old.x, old.y + 1, old.name)
      case 'D' => coordinate(old.x, old.y - 1, old.name)
  }

  private def moveTail(old: coordinate, head: coordinate, move: Char): coordinate = {
    if (old.distance(head) <= 1)
      old
    else {
      val tail = moveHead(old, move)
      coordinate(
        if ("UD".contains(move)) head.x else tail.x,
        if ("LR".contains(move)) head.y else tail.y,
        old.name
      )
    }
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
