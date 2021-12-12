object day5 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
  }

  case class coordinate(x: Int, y: Int)

  case class line(begin: coordinate, end: coordinate)

  def part1(): Long = {
    getLines
      .flatMap(l => {
        if (l.begin.x == l.end.x) {
          ((l.begin.y to l.end.y) ++ (l.end.y to l.begin.y)).map(y => coordinate(l.begin.x, y))
        } else if (l.begin.y == l.end.y) {
          ((l.begin.x to l.end.x) ++ (l.end.x to l.begin.x)).map(x => coordinate(x, l.begin.y))
        } else
          None
      })
      .toSeq
      .groupBy(identity)
      .view
      .mapValues(_.length)
      .count(y => y._2 >= 2)
  }

  def part2(): Long = {
    getLines
      .flatMap(l => {
        val deltaX = l.begin.x - l.end.x
        val deltaY = l.begin.y - l.end.y

        val stepX = 0.compare(deltaX)
        val stepY = 0.compare(deltaY)

        val steps = Seq(deltaX.abs, deltaY.abs).max

        (0 to steps)
          .map(s => coordinate(l.begin.x + (stepX * s), l.begin.y + (stepY * s)))
      })
      .toSeq
      .groupBy(x => s"${x.x},${x.y}")
      .view
      .mapValues(_.length)
      .count(y => y._2 >= 2)
  }

  def getLines: Iterator[line] = {
    """(\d)+""".r.findAllMatchIn(readInput())
      .map(_.matched.toInt)
      .sliding(4, 4)
      .map(m => line(coordinate(m(0), m(1)), coordinate(m(2), m(3))))
  }

  def readInput(): String = scala.io.Source.fromResource("day5.txt").getLines().mkString(" ")
}
