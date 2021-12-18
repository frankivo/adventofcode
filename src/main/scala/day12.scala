import scala.annotation.tailrec
import scala.collection.mutable

object day12 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    //    println(s"Part 2: ${part2()}")
  }

  extension (node: String) {
    def isSmall: Boolean = node.head.isLower
  }

  type CaveMap = Map[String, Set[String]]

  def part1(): Long = data("start").map(findEnd(_)).sum

  def findEnd(node: String, trail: Seq[String] = Seq("start")): Int = {
    if (node.isSmall && trail.contains(node))
      return 0

    data(node)
      .toSeq.map(c => {
      if (c == "end") 1
      else findEnd(c, trail :+ node)
    }).sum
  }

  def part2(): Long = 0

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
