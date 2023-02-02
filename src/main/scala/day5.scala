object day5 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
  }

  case class coordinate(x: Int, y: Int)

  case class line(begin: coordinate, end: coordinate) {
    def isDiagonal: Boolean = !(begin.x == end.x || begin.y == end.y)

    private val deltaX = begin.x - end.x

    private val deltaY = begin.y - end.y

    private val stepX: Int = 0.compare(deltaX)
    private val stepY: Int = 0.compare(deltaY)

    private val steps: Int = Seq(deltaX.abs, deltaY.abs).max

    def all: Seq[coordinate] = (0 to steps).map(s => coordinate(begin.x + (stepX * s), begin.y + (stepY * s)))
  }

  def part1(): Long = getOverlap(getLines.filterNot(_.isDiagonal))

  def part2(): Long = getOverlap(getLines)

  def getOverlap(data: Iterator[line]): Long = {
    data
      .flatMap(_.all)
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
