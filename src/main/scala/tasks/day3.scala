package com.github.frankivo
package tasks

object day3 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of priorities: ${getRucksackPrioritiesSum}")
  }

  private val priorities = (('a' to 'z') ++ ('A' to 'Z')).zip(LazyList.from(1)).toMap

  def getRucksackPrioritiesSum: Long = {
    input.foldLeft(0L) {
      (sum, cur) => {
        val doubleChar = cur._1.distinct.filter(c => cur._2.contains(c)).head
        sum + priorities(doubleChar)
      }
    }
  }

  def input: Seq[(String, String)] = util.get("day3.txt")
    .map(l => (l, l.length / 2))
    .map(l => (
      l._1.substring(0, l._2),
      l._1.substring(l._2)
    ))
}
