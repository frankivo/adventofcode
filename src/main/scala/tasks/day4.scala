package com.github.frankivo
package tasks

object day4 {
  def main(args: Array[String]): Unit = {
    input.foreach(i => {
      println(i(0))
    })
  }

  val input: Iterator[Array[(Int, Int)]] = "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8"
    .split("\n")
    .mkString(",")
    .split(",")
    .map(_.split("-") )
    .map(x=> (x(0).toInt, x(1).toInt))
    .grouped(2)
}
