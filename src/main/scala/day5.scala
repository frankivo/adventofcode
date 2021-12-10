object day5 {
  def main(args: Array[String]): Unit = {
    data
      .foreach(println)
  }

  case class line(start: (Int, Int), end: (Int, Int))

  def data(): Iterator[line] = {
    val alldata = scala.io.Source.fromResource("day5.txt").getLines().mkString(" ")
    """(\d)+""".r.findAllMatchIn(alldata)
      .map(_.matched.toInt)
      .sliding(4, 4)
      .map(m => line((m(0), m(1)), (m(2), m(3))))
  }
}
