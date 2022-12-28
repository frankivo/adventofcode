package com.github.frankivo
package tasks

object day14 {
  def main(args: Array[String]): Unit = {
    input.show()
  }

  extension (field: Set[coordinate]) {
    def charAt(x: Int, y: Int): Char = field.find(i => i.x == x && i.y == y).map(_.name.head).getOrElse('.')

    def show(): Unit = {
      (0 to field.maxBy(_.y).y).foreach(y =>
        (field.minBy(_.x).x to field.maxBy(_.x).x).foreach(x => print(field.charAt(x, y)))
        println
      )
    }
  }

  val input: Set[coordinate] = {
    util.get("day14.txt")
      .flatMap(l => {
        l.split("->").map(_.trim).sliding(2).toSeq
          .flatMap(p => {
            val c = p.map(_.split(",").map(_.toInt).toSeq)

            val xs = Seq(c.head.head, c.last.head).distinct.sorted
            val ys = Seq(c.head.last, c.last.last).distinct.sorted

            val horizontal = (xs.head to xs.last).map(x => coordinate(x, ys.head, "#"))
            val vertical = (ys.head to ys.last).map(y => coordinate(xs.head, y, "#"))
            val all = horizontal ++ vertical

            all
          })
      }).toSet + coordinate(500, 0, "+")
  }
}
