package com.github.frankivo
package tasks

object day5 {
  def main(args: Array[String]): Unit = {
    println(s"Top values: ${getTops(rearrange)}")
  }

  private val input: Seq[String] = util.get("day5.txt")

  private val stackPos: Map[Int, Int] = { // Pos, stacknr
    "[0-9]".r
      .findAllIn(input.find(_.trim.startsWith("1")).get)
      .matchData
      .map(m => (m.start, m.matched.toInt))
      .toMap
  }

  private val stacks: Map[Int, String] = {
    input
      .foldLeft(Seq[(Int, Char)]()) {
        (total, current) => {
          val crates = "[A-Z]".r
            .findAllIn(current)
            .matchData.map(m => (stackPos(m.start), m.matched.head))

          total ++ crates
        }
      }
      .groupBy(_._1)
      .map(x => (x._1, x._2.map(_._2).mkString))
  }

  case class Move(amount: Int, from: Int, to: Int)

  private val moves: Seq[Move] = {
    input
      .filter(_.startsWith("move"))
      .map(l => {
        val numbers = "([0-9]+)".r
          .findAllIn(l)
          .map(_.toInt)
          .toSeq
        Move(numbers.head, numbers(1), numbers(2))
      })
  }

  private def rearrange: Map[Int, String] = {
    moves
      .foldLeft(stacks) {
        (result, move) => {
          val tomove = result(move.from).take(move.amount)

          result.map {
            case (nr, stack) =>
              if (nr == move.from)
                (nr, stack.takeRight(stack.length - move.amount))
              else if (nr == move.to)
                (nr, tomove.reverse ++ stack)
              else
                (nr, stack)
          }
        }
      }
  }

  private def getTops(stacks: Map[Int, String]): String = stacks
    .toSeq
    .sortBy(_._1)
    .map(_._2.head)
    .mkString
}