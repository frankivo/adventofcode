object day6 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
  }

  def part1(): Long = {
    (0 until 80)
      .foldLeft(lanternfish) {
        (cur, step) => {
          val countDown = cur.map(_ - 1)
          (countDown ++ Seq.fill(countDown.count(_ == -1))(8))
            .map(f => if (f == -1) 6 else f)
        }
      }
      .length
  }

  def lanternfish: Seq[Int] = {
    scala.io.Source.fromResource("day6.txt")
      .getLines()
      .next()
      .split(",")
      .map(_.toInt)
  }
}
