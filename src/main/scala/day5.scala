object day5 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
    test()
  }

  def test(): Unit = {
    val ls = Seq(
      line(coordinate(8, 0), coordinate(0, 8)),
      line(coordinate(6, 4), coordinate(2, 0)),
      line(coordinate(0, 0), coordinate(8, 8)),
      line(coordinate(5, 5), coordinate(8, 2)),
    ).flatMap(l => Seq(l, line(l.end, l.begin)))

    ls.foreach(l => {
      val indices = {
        if (l.begin.x < l.end.x && l.begin.y < l.end.y)
          (l.begin.x to l.end.x, l.begin.y to l.end.y)
        else if (l.begin.x < l.end.x)
          (l.begin.x to l.end.x, l.end.y to l.begin.y)
        else if (l.begin.x > l.end.x && l.begin.y > l.end.y)
          (l.end.x to l.begin.x, l.end.y to l.begin.y)
        else
          (l.end.x to l.begin.x, l.begin.y to l.end.y)
      }
      val x = indices._2.zipWithIndex.map(y => coordinate(indices._1(y._2), y._1))
      println(x)
    })
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
        if (l.begin.x == l.end.x) {
          ((l.begin.y to l.end.y) ++ (l.end.y to l.begin.y)).map(y => coordinate(l.begin.x, y))
        } else if (l.begin.y == l.end.y) {
          ((l.begin.x to l.end.x) ++ (l.end.x to l.begin.x)).map(x => coordinate(x, l.begin.y))
        } else {
          val indices = {
            if (l.begin.x < l.end.x && l.begin.y < l.end.y)
              (l.begin.x to l.end.x, l.begin.y to l.end.y)
            else if (l.begin.x < l.end.x)
              (l.begin.x to l.end.x, l.end.y to l.begin.y)
            else if (l.begin.x > l.end.x && l.begin.y > l.end.y)
              (l.end.x to l.begin.x, l.end.y to l.begin.y)
            else
              (l.end.x to l.begin.x, l.begin.y to l.end.y)
          }
          indices._2.zipWithIndex.map(y => coordinate(indices._1(y._2), y._1))
        }
      })
      .toSeq
      .groupBy(identity)
      .view
      .mapValues(_.length)
      .count(y => y._2 >= 2)
  }

  def doStuff(xs: Seq[Int], ys: Seq[Int]): Seq[coordinate] = {
    for (y <- ys.zipWithIndex) yield coordinate(xs(y._2), y._1)
  }

  def getLines: Iterator[line] = {
    """(\d)+""".r.findAllMatchIn(readInput())
      .map(_.matched.toInt)
      .sliding(4, 4)
      .map(m => line(coordinate(m(0), m(1)), coordinate(m(2), m(3))))
  }

  def readInput(): String = scala.io.Source.fromResource("day5.txt").getLines().mkString(" ")
}
