package com.github.frankivo
package tasks

object day4 {
  def main(args: Array[String]): Unit = {
    input.foreach(println)
  }

  case class Range(min: Int, max: Int)

  val input = "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8"
    .split("\n")
    .map(_.split(","))
    .map(p => (p(0), p(1)))
    .map(p => (p._1.split("-"), p._2.split("-")))
    .map(p => (Range(p._1(0).toInt, p._1(1).toInt), Range(p._2(0).toInt, p._2(1).toInt)))
}
