package com.github.frankivo
package tasks

object day13 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    println(input.toSeq.head)
  }

  val input = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .sliding(2)
    .zip(LazyList.from(1))
}
