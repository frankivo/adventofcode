object day6 {
  def main(args: Array[String]): Unit = {
    lanternfish.foreach(println)
  }

  def lanternfish: Seq[Int] = {
    scala.io.Source.fromResource("day6.txt")
      .getLines()
      .next()
      .split(",")
      .map(_.toInt)
  }
}
