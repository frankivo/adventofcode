object day10 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  case class pair(left: Char, right: Char, score1: Int, score2: Int)

  def part1(): Int = {
    data
      .map(readLine)
      .flatMap(line => line.filterNot(_.isLeft).headOption)
      .map(_.findPair)
      .map(_.score1)
      .sum
  }

  def part2(): Long = {
    val sums = data
      .map(readLine)
      .filterNot(line => line.exists(!_.isLeft))
      .map(_.reverse)
      .map(_.map(_.findPair.score2))
      .map(_.foldLeft(0L) { (total, score) => (total * 5) + score })
      .sorted

    val index = sums.length / 2
    sums.drop(index).dropRight(index).head
  }

  def readLine(line: String): String = {
    line
      .foldLeft("")({
        (store, cur) =>
          if (!cur.isLeft && cur.findPair.left == store.last)
            store.dropRight(1)
          else
            store + cur
      })
  }

  extension (c: Char) {
    def isLeft: Boolean = charPairs.exists(_.left == c)

    def findPair: pair = Seq(charPairs.find(_.left == c), charPairs.find(_.right == c)).flatten.head
  }

  val charPairs: Seq[pair] = {
    Seq(
      pair('(', ')', 3, 1),
      pair('[', ']', 57, 2),
      pair('{', '}', 1197, 3),
      pair('<', '>', 25137, 4),
    )
  }

  val data: Seq[String] = io.Source.fromResource("day10.txt").getLines().toSeq
}
