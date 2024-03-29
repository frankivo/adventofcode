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
      val min = rope.map(k => Seq(k.x, k.y).min).min
      val max = rope.map(k => Seq(k.x, k.y).max).max

      for (y <- (min to max).reverse) {
        for (x <- min to max) {
          print(rope.find(k => k.x == x && k.y == y).map(_.name).getOrElse("."))
        }
        println()
      }
      println()
    }
  }

  private def amountTailPositions(tailSize: Int): Int = {
    val startPos = coordinate(0, 0, "H")
    val tail = (1 to tailSize).map(i => coordinate(0, 0, i.toString))
    val start = ropestate(startPos, tail, tail.takeRight(1).toSet)

    val history = moves.foldLeft(start) {
      (his, move) => {
        val head = moveHead(his.head, move)
        val tail = his.tail.foldLeft(Seq[coordinate]()) {
          (visited, cur) => {
            val prev = visited.lastOption.getOrElse(head)
            visited :+ moveTail(cur, prev)
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
      val x = util.clamp(prev.x - cur.x, -1, 1)
      val y = util.clamp(prev.y - cur.y, -1, 1)

      coordinate(cur.x + x, cur.y + y, cur.name)
    }
  }

  private val moves: String = util.get(9)
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
