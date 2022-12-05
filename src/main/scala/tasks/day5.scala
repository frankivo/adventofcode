package com.github.frankivo
package tasks

object day5 {
  def main(args: Array[String]): Unit = {
    println(stacks)
  }

  private val input: Array[String] = "   [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2"
    .split("\n")

   private val stacks: Map[Int, Seq[(Int, Char)]] = {
    input
      .foldLeft(Seq[(Int, Char)]()) {
        (total, current) => {
          val crates = "[A-Z]".r
            .findAllIn(current)
            .matchData.map(m => {
            val stack = (m.start / 3) + 1
            val crate = m.matched.head
            (stack, crate)
          })
          total ++ crates
        }
      }
    .groupBy(_._1)
  }
}