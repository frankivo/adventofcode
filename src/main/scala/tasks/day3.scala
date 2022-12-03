package com.github.frankivo
package tasks

object day3 {
  def main(args: Array[String]): Unit = {
input.map(x => (x._1.length, x._2.length)).foreach(println)
  }

  def input: Seq[(String, String)] = Seq(
    "vJrwpWtwJgWrhcsFMMfFFhFp",
    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
    "PmmdzqPrVvPwwTWBwg",
    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
    "ttgJtRGJQctTZtZT",
    "CrZsJsPPZsGzwwsLwLmpwMDw"
  )
    .map(l => (l, l.length / 2))
    .map(l => (
      l._1.substring(0, l._2),
      l._1.substring(l._2)
    ))
}
