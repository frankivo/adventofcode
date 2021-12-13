object day6 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${bruteForce(80)}")
  }

  def bruteForce(days: Int): Long = {
    (0 until days)
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
