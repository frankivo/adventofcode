import scala.util.Try

object day9 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    //    println(s"Part2: ${part2()}")
  }

  case class cell(x: Int, y: Int) {
    def getOption: Option[cell] = {
      if (x < 0) None
      else if (y < 0) None
      else if (x >= data.length) None
      else if (y >= data.length) None
      else Some(cell.this)
    }

    def value: Int = data(x)(y)

    def isLowest: Boolean = adjacent.forall(value < _.value)

    private def adjacent: Seq[cell] = {
      Seq(
        (x - 1, y), // Top
        (x, y - 1), // Left
        (x + 1, y), // Down
        (x, y + 1) // Right
      ).flatMap(c => cell(c._1, c._2).getOption)
    }
  }

  def part1(): Int = {
    data.zipWithIndex
      .flatMap(row => {
        row._1.zipWithIndex
          .flatMap(column => {
            val cur = cell(row._2, column._2)
            if (cur.isLowest)
              Some(cur.value)
            else None
          })
      })
      .map(_ + 1)
      .sum
  }

  def part2(): Int = {
    ???
  }

  val data: Seq[Seq[Int]] = {
    scala.io.Source.fromResource("day9.txt").getLines()
      .map(_.toCharArray.map(_.getNumericValue).toSeq)
      .toSeq
  }
}
