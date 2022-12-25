package com.github.frankivo
package tasks

object day13 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    println(rawInput)
    println(compare(rawInput.head))
  }

  private def compare(pair: Pair): Boolean = {
    println(pair.left)
    println(pair.right)

    val left = pair.left.split(",").toSeq
    val right = pair.right.split(",").toSeq

    left.indices.forall(i => left(i) <= right(i))
  }

  private case class Pair(index: Int, left: String, right: String)

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}
