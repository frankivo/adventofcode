object day4 {
  case class field(num: Int, hit: Boolean = false)

  type board = Seq[Seq[field]]

  def main(args: Array[String]): Unit = {
    println(draws)
    var brds = boards().toSeq

    draws
      .foreach(d => brds = brds.map(updateBoard(_, d)))
    brds.foreach(println)
  }


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
          .map(field(_))
          .toSeq
      })
      .sliding(5, 5)
  }

  def updateBoard(board: board, draw: Int): board = {
    board.map(l => {
      l.map(f => if (f.num == draw) field(f.num, true) else f)
    })
  }
}
