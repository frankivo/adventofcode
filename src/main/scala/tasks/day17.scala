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

    end.show()
    println(end)
  }

  extension (field: Set[coordinate]) {
    private def height: Int = field.maxByOption(_.y).map(_.y).getOrElse(0)

    private def addRock(): Set[coordinate] = field ++ rocks.next(height)

    private def show(): Unit = {
      (0 to height).reverse.foreach(y => {
        (0 to 7).foreach(x => {
          print(field.find(c => c.y == y && c.x == x).map(_.name).getOrElse("."))
        })
        println()
      })
    }
  }

  private class Jetstream {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)
  }

  private class RockFactory {
    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next(top: Int): Seq[coordinate] = {
      val shape = iterator.next() % 5
      shape match
        case 0 => // Horizontal line
          (2 to 5).map(i => coordinate(i, top + 3, "@"))
    }
  }

}
