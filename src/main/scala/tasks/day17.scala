package com.github.frankivo
package tasks


object day17 {
  def main(args: Array[String]): Unit = {
    (0 to 4000).map(_ => jets.next).foreach(println)
  }

  private val moveCount: Int = 2022

  private class Jets {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)
  }

  private val jets: Jets = Jets()
}
