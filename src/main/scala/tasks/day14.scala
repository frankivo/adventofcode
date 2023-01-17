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

  private def part2: Long = explore + 1

  private def countSand(start: Field): Long = {
    Seq.unfold(start) {
      field => {
        val update = field.addSand()
        if (update.isEmpty) None
        else Some(update.get.count(f => f.name == sand), update.get)
      }
    }.max
  }

  private def explore: Int = {
    val field = fieldWithFloor
    val explorable = Set[coordinate](sandSource)

    Seq.unfold(field, explorable) {
      (f, e) => {
        if (e.isEmpty) None
        else {
          val cur = e.minBy(_.y)

          val adj = cur.adjecent.filterNot(a => f.isBlocked(a.x, a.y))

          val newF = f ++ adj
          val newE = e.filterNot(_.eq(cur)) ++ adj.filterNot(f.contains)

          val sandC = newF.count(_.name.eq(sand))

          Some(sandC, (newF, newE))
        }
      }
    }.last
  }

  extension (coord: coordinate) {
    private def adjecent: Seq[coordinate] = {
      if (coord.y + 1 > fieldWithFloor.depth) Seq()
      else
        Seq(
          coordinate(coord.x, coord.y + 1, sand),
          coordinate(coord.x - 1, coord.y + 1, sand),
          coordinate(coord.x + 1, coord.y + 1, sand),
        )
    }
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
      val end = Seq.unfold(sandSource) {
        state => {
          if (state.y + 1 > depth + 2) None
          else {
            if (!isBlocked(state.x, state.y + 1))
              Some(coordinate(state.x, state.y + 1, sand), coordinate(state.x, state.y + 1))
            else if (!isBlocked(state.x - 1, state.y + 1))
              Some(coordinate(state.x - 1, state.y + 1, sand), coordinate(state.x - 1, state.y + 1))
            else if (!isBlocked(state.x + 1, state.y + 1))
              Some(coordinate(state.x + 1, state.y + 1, sand), coordinate(state.x + 1, state.y + 1))
            else None
          }
        }
      }.last
      Option.when(end.y <= depth)(field + end)
    }

    def isBlocked(x: Int, y: Int): Boolean = Seq(rock, sand).contains(field.itemAt(x, y))
  }

  private val fieldWithoutFloor: Field = {
    util.get(14)
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

  private val fieldWithFloor: Field = {
    val field = fieldWithoutFloor
    val floorDepth = field.depth + 2

    field ++
      ((field.left - 500) to (field.right + 500)).map(x => coordinate(x, floorDepth, rock))
  }
}
