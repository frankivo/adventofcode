import scala.util.Try

object day9 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    //    println(s"Part2: ${part2()}")
  }

  case class cell(row: Int, col: Int) {
    def getOption: Option[cell] = {
      if (col < 0) None
      else if (row < 0) None
      else if (col >= width) None
      else if (row >= height) None
      else Some(cell.this)
    }

    def value: Int = data(row)(col)

    def isLowest: Boolean = adjacent.forall(value < _.value)

    private def adjacent: Seq[cell] = {
      Seq(
        (row, col - 1), // Left
        (row - 1, col), // Top
        (row, col + 1), // Right
        (row + 1, col), // Bottom
      )
        .map(c => cell(c._1, c._2))
        .flatMap(_.getOption)
    }
  }

  def part1(): Int = {
    getLowPoints
      .map(_.value)
      .map(_ + 1)
      .sum
  }

  def part2(): Int = {
    ???
  }

  def getLowPoints: Seq[cell] = {
    (0 until height)
      .flatMap(row => {
        (0 until width)
          .flatMap(column => {
            val cur = cell(row, column)
            if (cur.isLowest)
              Some(cur)
            else None
          })
      })
  }

  val data: Seq[Seq[Int]] = {
    scala.io.Source.fromResource("day9.txt").getLines()
      .map(_.toCharArray.map(_.getNumericValue).toSeq)
      .toSeq
  }

  val height: Int = data.length

  val width: Int = data.head.length
}
