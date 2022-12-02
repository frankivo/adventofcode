package com.github.frankivo
package tasks

object day2 {

  def main(args: Array[String]): Unit = {
    println(s"Total score: ${getTotalScore()}")
  }

  private val hands = Map(
    'A' -> "rock",
    'B' -> "paper",
    'C' -> "scissors",
    'X' -> "rock",
    'Y' -> "paper",
    'Z' -> "scissors",
  )

  private def getTotalScore(): Long = {
    input.foreach(i => {
      if (hands(i._1) equals hands(i._2))
        println("draw")
    })
    0
  }

  private val input: Seq[(Char, Char)] = Seq("A Y", "B X", "C Z")
    .map(_.filter(('A' to 'Z').toSet))
    .map(l => (l(0), l(1)))
}
