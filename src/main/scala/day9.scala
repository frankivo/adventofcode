import scala.util.Try

object day9 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    //    println(s"Part2: ${part2()}")
  }

  // 1820 too high
  // 27650 too high
  def part1(): Int = {
   val x = data.zipWithIndex
      .flatMap(row => {
        row._1.zipWithIndex
          .flatMap(column => {
            val cur = cell(row._2, column._2).get
            val adj = adjacent(row._2, column._2)
            if (adj.count(_ < cur) == 0)
              Some(cur)
            else
              None
          })
      })
      .map(_ + 1)

    println(x)
    0
  }

  def part2(): Int = {
    ???
  }

  def cell(row: Int, column: Int): Option[Int] = Try(data(row)(column)).toOption

  def adjacent(row: Int, column: Int): Seq[Int] = {
    Seq(
      (row - 1, column), // Top
      (row, column - 1), // Left
      (row + 1, column), // Down
      (row, column + 1) // Right
    ).flatMap(c => cell(c._1, c._2))
  }

  val data: Seq[Seq[Int]] = {
    scala.io.Source.fromResource("day9.txt").getLines()
      .map(_.toCharArray.map(_.getNumericValue).toSeq)
      .toSeq
  }

  val width: Int = data.head.length

  val height: Int = data.length
}
