package com.github.frankivo
package tasks

object day14 {
  def main(args: Array[String]): Unit = {
    println(s"Max items of sand before falling off: $part1")
    println(s"Max items of sand before hitting top: $part2")
  }

  private type Field = Set[coordinate]

  private val air: String = "."
  private val rock: String = "#"
  private val sand: String = "o"
  private val sandSource: coordinate = coordinate(500, 0, "+")

  private def part1: Long = countSand(fieldWithoutFloor)

  private def part2: Long = countSand(fieldWithFloor) + 1

  private def countSand(start: Field): Long = {
    Seq.unfold(start) {
      field => {
        val update = field.addSand()
        if (update.isEmpty) None
        else Some(update.get.count(f => f.name == sand), update.get)
      }
    }.max
  }

  extension (field: Field) {
    def itemAt(x: Int, y: Int): String = field.find(i => i.x == x && i.y == y).map(_.name).getOrElse(air)

    def show(): Unit = {
      (0 to depth).foreach(y =>
        (left to right).foreach(x => print(field.itemAt(x, y)))
        println
      )
    }

    def depth: Int = field.maxBy(_.y).y

    def left: Int = field.minBy(_.x).x

    def right: Int = field.maxBy(_.x).x

    def addSand(): Option[Field] = {
      var canMove = true
      var x = sandSource.x
      var y = sandSource.y

      while (canMove) {
        if (!isBlocked(x, y + 1))
          y += 1 // fall down!
        else if (!isBlocked(x - 1, y + 1)) {
          y += 1 // fall left
          x -= 1
        }
        else if (!isBlocked(x + 1, y + 1)) {
          y += 1 // fall right
          x += 1
        }
        else
          canMove = false

        if (y > depth)
          canMove = false
        if (sandSource.x == x && sandSource.y == y)
          canMove = false
      }

      Option.when(y <= depth && !(sandSource.x == x && sandSource.y == y))(field + coordinate(x, y, sand))
    }

    def isBlocked(x: Int, y: Int): Boolean = Seq(rock, sand).contains(field.itemAt(x, y))
  }

  private def fieldWithoutFloor: Field = {
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

  private def fieldWithFloor: Field = {
    val field = fieldWithoutFloor
    val floorDepth = field.depth + 2

    field ++
      ((field.left - 500) to (field.right + 500)).map(x => coordinate(x, floorDepth, rock))
  }
}
