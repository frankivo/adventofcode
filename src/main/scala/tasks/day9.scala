package com.github.frankivo
package tasks

object day9 {
  def main(args: Array[String]): Unit = {
    println(moves)
  }

  private val moves: String = util.get("day9.txt")
    .map(_.split(" "))
    .map(m => (m(0), m(1).toInt))
    .flatMap(m => m._1 * m._2)
    .mkString
}
