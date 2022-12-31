package com.github.frankivo
package tasks

object day15 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    println(input)
  }

  private val input : Set[coordinate] = {
    util.get("day15.txt")
      .flatMap("""([\-0-9]+)""".r.findAllMatchIn)
      .map(_.matched.toInt)
      .grouped(4)
      .flatMap(g => {
        Seq(
          coordinate(g(0), g(1), "S"),
          coordinate(g(2), g(3), "B"),
        )
      })
      .toSet
  }
}
