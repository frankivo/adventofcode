import scala.annotation.tailrec
import scala.collection.mutable

object day12 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  extension (node: String) {
    def isSmall: Boolean = node.head.isLower
  }

  type CaveMap = Map[String, Set[String]]

  def part1(): Long = data("start").map(part1(_)).sum

  def part1(node: String, trail: Seq[String] = Seq("start")): Int = {
    if (node.isSmall && trail.contains(node))
      return 0

    data(node)
      .toSeq.map(c => {
      if (c == "end") 1
      else part1(c, trail :+ node)
    }).sum
  }

  def part2(): Long = data("start").map(part2(_)).sum

  def part2(node: String, trail: Seq[String] = Seq("start")): Int = {
    if (node.isSmall) {
      if (Seq("start", "end").contains(node))
        return 0

      val pair = trail
        .filter(_.isSmall)
        .filterNot(Seq("start", "end").contains(_))
        .groupBy(identity)
        .find(_._2.length == 2)
        .map(_._1)

      if (pair.isDefined) {
        if (pair.get == node)
          return 0
        else if (trail.contains(node))
          return 0
      }
    }

    data(node)
      .toSeq
      .map(c => {
        if (c == "end") {
          println((trail :+ c).mkString(","))
          1
        }
        else part2(c, trail :+ node)
      }).sum
  }

  val data: CaveMap = {
    io.Source.fromResource("day12.txt").getLines()
      .map(_.split("-").toSeq)
      .map(l => (l.head, l.last))
      .flatMap(l => Seq(l, (l._2, l._1)))
      .foldLeft(Map[String, Set[String]]()) {
        (store, line) => {
          if (!store.contains(line.head))
            store + (line._1 -> Set(line._2))
          else
            store.map((k, v) => {
              if (k == line._1)
                (k, v + line._2)
              else
                (k, v)
            })
        }
      }
  }
}
