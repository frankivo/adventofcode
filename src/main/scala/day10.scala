object day10 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    //    println(s"Part 2: ${part2()}")
  }

  case class pair(left: Char, right: Char, score: Int)

  def part1(): Int = {
    data.flatMap(line => {
      line
        .foldLeft("")({
          (store, cur) =>
            if (!cur.isLeft && cur.findPair.left == store.last)
              store.dropRight(1)
            else
              store + cur
        })
        .filterNot(_.isLeft)
        .headOption
    })
      .map(_.findPair)
      .map(_.score)
      .sum
  }

  def part2(): Int = ???

  extension (c: Char) {
    def isLeft: Boolean = charPairs.exists(_.left == c)

    def findPair: pair = Seq(charPairs.find(_.left == c), charPairs.find(_.right == c)).flatten.head
  }

  val scoringTable: Map[Char, Int] = Map(
    ')' -> 3,
    ']' -> 57,
    '}' -> 1197,
    '>' -> 25137,
  )

  val charPairs: Seq[pair] = {
    "()[]{}<>"
      .sliding(2, 2)
      .map(c => pair(c.head, c.last, scoringTable(c.last)))
      .toSeq
  }

  val data: Iterator[String] = io.Source.fromResource("day10.txt").getLines()

}
