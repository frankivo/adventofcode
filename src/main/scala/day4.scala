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

    def score: Long = data.flatten.filterNot(_.hit).map(_.num).sum
  }

  def main(args: Array[String]): Unit = {
    part1()
    part2()
  }

  def part1(): Unit = {
    var brds = boards.toSeq
    var winDraw = -1

    val drws = draws.iterator

    while findWin(brds).isEmpty
    do {
      val draw = drws.next()
      brds = brds.map(b => b.update(draw))
      winDraw = draw
    }

    val sum = findWin(brds).getOrElse(throw new Exception("No win found")).score
    println(s"Board sum: $sum")
    println(s"Winning draw: $winDraw")
    println(s"Answer: ${sum * winDraw}")
  }

  def part2(): Unit = {
    var brds = boards.toSeq

    val finalWin = draws
      .flatMap(d => {
        val games = brds.map(_.update(d))
        val ret = {
          if (countWins(games) > 0)
            Some((d, games.find(_.isWin).get))
          else
            None
        }
        brds = games.filterNot(_.isWin)
        ret
      })
      .last

    val sum = finalWin._2.score
    val draw = finalWin._1

    println(s"Board sum: $sum")
    println(s"Winning draw: ${finalWin._1}")
    println(s"Answer: ${sum * draw}")
  }

  def allData(): Iterator[String] = scala.io.Source.fromResource("day4.txt").getLines()

  val draws: Seq[Int] = allData().next().split(",").map(_.toInt)

  def boards: Iterator[board] = {
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

  def countWins(data: Seq[board]): Long = data.count(_.isWin)
}
