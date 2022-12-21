package com.github.frankivo
package tasks

object day12 {
  def main(args: Array[String]): Unit = {
    println(input)
  }

  val input: Seq[coordinate] = {
    util.get("day12.txt")
      .zipWithIndex.flatMap(y =>
      y._1.zipWithIndex
        .map(x => coordinate(x = x._2, y = y._2, name = x._1.toString))
    )
  }
}
