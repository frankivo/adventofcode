package com.github.frankivo
package tasks

object day2 {

  def main(args: Array[String]): Unit = {
    println(s"Total score: $getTotalScore")
  }

  private val hands = Map(
    'A' -> "rock",
    'B' -> "paper",
    'C' -> "scissors",
    'X' -> "rock",
    'Y' -> "paper",
    'Z' -> "scissors",
  )

  private def getTotalScore: Long = {
    input.foldLeft(0L) {
      (total, line) => {

        val opponent = hands(line._1)
        val player = hands(line._2)

        if (opponent equals player)
          total + 6
        else if (opponent == "rock" && player == "paper")
          total + 8
        else
          total + 1
      }
    }
  }

  private val input: Seq[(Char, Char)] = Seq("A Y", "B X", "C Z")
    .map(_.filter(('A' to 'Z').toSet))
    .map(l => (l(0), l(1)))
}
