object day5 {
  def main(args: Array[String]): Unit = {
    println(s"Score: ${play()}")
  }

  case class coordinate(x: Int, y: Int)

  case class line(begin: coordinate, end: coordinate)

  def play(): Long = {
    getLines
      .flatMap(l => {
        if (l.begin.x == l.end.x) {
          ((l.begin.y to l.end.y) ++ (l.end.y to l.begin.y)).map(y => coordinate(l.begin.x, y))
        } else if (l.begin.y == l.end.y) {
          ((l.begin.x to l.end.x) ++ (l.end.x to l.begin.x)).map(x => coordinate(x, l.begin.y))
        } else None
      })
      .toSeq
      .groupBy(identity)
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
