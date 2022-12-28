package com.github.frankivo
package tasks

object day14 {
  def main(args: Array[String]): Unit = {
    input.foreach(println)
  }

  val input: Set[coordinate] = {
    util.get("day14.txt")
      .drop(1)
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
      }).toSet
  }
}
