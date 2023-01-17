package com.github.frankivo
package tasks

object day4 {
  def main(args: Array[String]): Unit = {
    println(s"Amount of included ranges: $getIncludedRangeCount")
    println(s"Amount of overlapping ranges: $getOverlappingRangesCount")
  }

  case class Range(min: Int, max: Int)

  def getIncludedRangeCount: Long = {
    input.count(i => {
      (i._1.min <= i._2.min && i._1.max >= i._2.max) ||
        (i._2.min <= i._1.min && i._2.max >= i._1.max)
    })
  }

  def getOverlappingRangesCount: Long = {
    input.count(i => {
      (i._1.min to i._1.max)
        .exists(j => (i._2.min to i._2.max).contains(j))
    })
  }

  def input: Iterator[(Range, Range)] = util.get(4)
    .mkString(",")
    .split(",")
    .map(_.split("-"))
    .map(x => Range(x(0).toInt, x(1).toInt))
    .grouped(2)
    .map(x => (x(0), x(1)))
}
