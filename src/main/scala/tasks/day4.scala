package com.github.frankivo
package tasks

object day4 {
  def main(args: Array[String]): Unit = {
    println(s"Amount of included ranges: $getIncludedRangeCount")
  }

  def getIncludedRangeCount: Long = {
    input.count(i => {
      (i(0)._1 <= i(1)._1 && i(0)._2 >= i(1)._2) ||
        (i(1)._1 <= i(0)._1 && i(1)._2 >= i(0)._2)
    })
  }

  val input: Iterator[Array[(Int, Int)]] = util.get("day4.txt")
    .mkString(",")
    .split(",")
    .map(_.split("-"))
    .map(x => (x(0).toInt, x(1).toInt))
    .grouped(2)
}
