import scala.collection.mutable

object day12 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  case class Node(name: String, connections: Set[String]) {
    val isSmall: Boolean = name.head.isLower
  }

  type CaveMap = Map[String, Set[String]]

  extension (cv: CaveMap) {
    def start: Set[String] = cv("start")
  }

  def part1(): Long = {
    readData
      .start
      .map(node => {
        node

      })

    0
  }

  def part2(): Long = 0

  def readData: CaveMap = {
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
