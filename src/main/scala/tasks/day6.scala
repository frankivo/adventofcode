package com.github.frankivo
package tasks

object day6 {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    println(marker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))


  }

  def marker(msg: String): Int = msg
    .sliding(4, 1)
    .zipWithIndex
    .take(4)
    .find(_._1.distinct.length == 4)
    .map(_._2)
    .get + 4
}
