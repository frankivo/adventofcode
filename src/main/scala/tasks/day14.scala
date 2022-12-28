package com.github.frankivo
package tasks

object day14 {
  def main(args: Array[String]): Unit = {
    println(part1)
  }

  private type Field = Set[coordinate]

  private val air: String = "."
  private val rock: String = "#"
  private val sand: String = "o"

  def part1: Long = {
    val end = (0 until 24).foldLeft(input) {
      (field, _) => field.addSand()
    }
    end.show()
    0
  }

  extension (field: Set[coordinate]) {
    def itemAt(x: Int, y: Int): String = field.find(i => i.x == x && i.y == y).map(_.name).getOrElse(air)

    def show(): Unit = {
      (0 to field.maxBy(_.y).y).foreach(y =>
        (field.minBy(_.x).x to field.maxBy(_.x).x).foreach(x => print(field.itemAt(x, y)))
        println
      )
    }

    def addSand(): Field = {
      var canMove = true
      var x = sandSource.x
      var y = sandSource.y

      while (canMove) {
        if (!isBlocked(x, y + 1))
          y += 1 // fall down!
        else if (!isBlocked(x - 1, y + 1)) {
          y += 1
          x -= 1
        }
        else if (!isBlocked(x + 1, y + 1)) {
          y += 1
          x += 1
        }
        else
          canMove = false
      }

      field + coordinate(x, y, sand)
    }

    def isBlocked(x: Int, y: Int): Boolean = Seq(rock, sand).contains(field.itemAt(x, y))
  }

  val sandSource: coordinate = coordinate(500, 0, "+")

  val input: Set[coordinate] = {
    util.get("day14.txt")
      .flatMap(l => {
        l.split("->").map(_.trim).sliding(2).toSeq
          .flatMap(p => {
            val c = p.map(_.split(",").map(_.toInt).toSeq)

            val xs = Seq(c.head.head, c.last.head).distinct.sorted
            val ys = Seq(c.head.last, c.last.last).distinct.sorted

            val horizontal = (xs.head to xs.last).map(x => coordinate(x, ys.head, rock))
            val vertical = (ys.head to ys.last).map(y => coordinate(xs.head, y, rock))
            val all = horizontal ++ vertical

            all
          })
      }).toSet + sandSource
  }
}
