object day4 {
  def main(args: Array[String]): Unit = {
    println(draws)
    boards()
      .foreach(println)
  }

  type board = Seq[Seq[Int]]

  def allData(): Iterator[String] = scala.io.Source.fromResource("day4.txt").getLines()

  val draws: Seq[Int] = allData().next().split(",").map(_.toInt)

  def boards(): Iterator[board] = {
    allData()
      .drop(2)
      .filterNot(_.isEmpty)
      .map({
        _.split(" ")
          .filterNot(_.isEmpty)
          .map(_.toInt)
          .toSeq
      })
      .sliding(5, 5)
  }
}
