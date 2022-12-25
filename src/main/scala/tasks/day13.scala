package com.github.frankivo
package tasks

object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of sorted pair indices: ${part1}")
  }

  def part1 : Long = {
    rawInput
      .foldLeft(0L) {
        (sum, pair) => {
          sum + pair.index // If pair is sorted, else 0
        }
      }
  }

  private case class Pair(index: Int, left: String, right: String)

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}
