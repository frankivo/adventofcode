import scala.util.Try

object day9 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    //    println(s"Part2: ${part2()}")
  }

  def part1(): Int = {
    data.zipWithIndex
      .flatMap(row => {
        row._1.zipWithIndex.flatMap(column => {
          val cur = cell(row._2, column._2).get
          if (adjacent(row._2, column._2).count(_ < cur) == 0)
            Some(cur)
          else None
        })
      })
      .map(_ + 1)
      .sum
  }

  def part2(): Int = {
    ???
  }

  def cell(row: Int, column: Int): Option[Int] = Try(data(row)(column).toString.toInt).toOption

  def adjacent(row: Int, column: Int): Seq[Int] = {
    Seq(
      (row - 1, column), // Top
      (row, column - 1), // Left
      (row + 1, column), // Down
      (row, column + 1) // Right
    ).flatMap(c => cell(c._1, c._2))
  }

  val data: Seq[String] = scala.io.Source.fromResource("day9.txt").getLines().toSeq

  val width: Int = data.head.length

  val height: Int = data.length
}
