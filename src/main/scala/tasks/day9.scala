package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    println(s"Amount of unique tail positions: ${amountTailPositions(1)}")
    println(s"Amount of unique tail positions: ${amountTailPositions(9)}")
  }

  case class ropestate(head: coordinate, tail: Seq[coordinate], tailPositions: Set[coordinate]) {
    def show(): Unit = {
      val rope = head +: tail
      val max = rope.map(k => Seq(k.x, k.y).max).max

      for (y <- (0 to max).reverse) {
        for (x <- 0 to max) {
          print(rope.find(k => k.x == x && k.y == y).map(_.name).getOrElse("."))
        }
        println()
      }
    }
  }

  private def amountTailPositions(tailSize: Int): Int = {
    val startPos = coordinate(0, 0, "H")
    val tail = (1 to tailSize).map(i => coordinate(0, 0, i.toString))
    val start = ropestate(startPos, tail, Set(startPos))

    val history = moves.foldLeft(start) {
      (his, move) => {
        val head = moveHead(his.head, move)
        val tail = his.tail.foldLeft(Seq[coordinate]()) {
          (x, cur) => {
            val prev = x.lastOption.getOrElse(head)
            x :+ moveTail(cur, prev)
          }
        }

        ropestate(head, tail, his.tailPositions + tail.last)
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

  private def moveTail(cur: coordinate, prev: coordinate): coordinate = {
    if (cur.distance(prev) <= 1)
      cur
    else {
      Seq(
        coordinate(cur.x + 1, cur.y + 1, cur.name),
        coordinate(cur.x - 1, cur.y - 1, cur.name),
        coordinate(cur.x + 1, cur.y - 1, cur.name),
        coordinate(cur.x - 1, cur.y + 1, cur.name),
        coordinate(cur.x + 1, cur.y, cur.name),
        coordinate(cur.x - 1, cur.y, cur.name),
        coordinate(cur.x, cur.y + 1, cur.name),
        coordinate(cur.x, cur.y - 1, cur.name),
      )
        .find(_.distance(prev) <= 1).get
    }
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
