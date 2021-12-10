object day5 {
  def main(args: Array[String]): Unit = {
    getLines
      .foreach(println)

    diagram()

    Seq(
      line(coordinate(1, 3), coordinate(1, 5)),
      line(coordinate(4, 4), coordinate(8, 4)),
      line(coordinate(8, 4), coordinate(4, 4)),
    )
      .foreach(i => println(i.all))
  }

  case class coordinate(x: Int, y: Int)

  case class line(begin: coordinate, end: coordinate) {
    val sorted: Seq[coordinate] = Seq(begin, end).sorted((a, b) => a.x.compare(b.x) + a.y.compare(a.y))
    val smallest: coordinate = sorted.head
    val biggest: coordinate = sorted.last

    val all: Seq[coordinate] = {
      val x = (smallest.x to biggest.x).map(c => coordinate(c, begin.y))
      val y = (smallest.y to biggest.y).map(c => coordinate(begin.x, c))
      (x ++ y).distinct
    }
  }

  def diagram(): Unit = {
    println(s"$width * $height")

    val grid = Array.ofDim[Int](height, width)

    val filled = getLines
      .foldLeft(grid) {
        (g, l) => {
          g.map(row => {
            row.map(col => {
              col
            })
          })
        }
      }

    filled.map(r => r.mkString(", ")).foreach(println)
  }

  val coordinates: Seq[coordinate] = getLines.flatMap(i => Seq(i.begin, i.end)).toSeq

  val height: Int = coordinates.max((a, b) => a.y.compare(b.y)).y

  val width: Int = coordinates.max((a, b) => a.x.compare(b.x)).x

  def getLines: Iterator[line] = {
    """(\d)+""".r.findAllMatchIn(readInput())
      .map(_.matched.toInt)
      .sliding(4, 4)
      .map(m => line(coordinate(m(0), m(1)), coordinate(m(2), m(3))))
  }

  def readInput(): String = scala.io.Source.fromResource("day5.txt").getLines().mkString(" ")
}
