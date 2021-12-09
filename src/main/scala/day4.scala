object day4 {
  case class field(num: Int, hit: Boolean = false)

  case class board(data: Seq[Seq[field]]) {
    def isWin: Boolean = {
      val vert = data.count(b => b.forall(_.hit)) > 0
      val horz = (0 until 5).count(i => data.forall(_ (i).hit)) > 0
      vert || horz
    }

    def update(draw: Int): board = {
      board(data.map(l => l.map(f => if (f.num == draw) field(f.num, true) else f)))
    }
  }

  def main(args: Array[String]): Unit = {
    var brds = boards.toSeq
    var winDraw = -1

    while findWin(brds).isEmpty
    do {
      val draw = draws.next()
      brds = brds.map(b => b.update(draw))
      winDraw = draw
    }

    val sum = countWin(findWin(brds).getOrElse(throw new Exception("No win found")))
    println(s"Board sum: $sum")
    println(s"Winning draw: $winDraw")
    println(s"Answer: ${sum * winDraw}")
  }

  def allData(): Iterator[String] = scala.io.Source.fromResource("day4.txt").getLines()

  val draws: Iterator[Int] = allData().next().split(",").map(_.toInt).iterator

  val boards: Iterator[board] = {
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
      .map(board.apply)
  }

  def findWin(data: Seq[board]): Option[board] = data.find(_.isWin)

  def countWin(brd: board): Long = brd.data.flatten.filterNot(_.hit).map(_.num).sum
}
