package com.github.frankivo
package tasks


object day17 {
  def main(args: Array[String]): Unit = {
    part1()
  }

  private val jets = Jetstream()
  private val rocks = RockFactory()

  private val moveCount: Int = 2022

  private def part1(): Unit = {
    val start = Set.empty[coordinate]

    val end = (0 until 1).foldLeft(start) {
      (state, _) => {
        state.addRock()
      }
    }

    println(end)
  }

  extension (field: Set[coordinate]) {
    def height: Int = field.maxByOption(_.y).map(_.y).getOrElse(0)

    def addRock(): Set[coordinate] = field ++ rocks.next(height)
  }

  private class Jetstream {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)
  }

  private class RockFactory {
    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next(top: Int): Set[coordinate] = {
      val shape = iterator.next() % 5
      shape match
        case _ => // Horizontal line
          (3 to 6).map(i => coordinate(i, top + 4)).toSet
    }
  }

}
