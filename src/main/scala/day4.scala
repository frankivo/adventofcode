object day4 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  case class field(num: Int, hit: Boolean = false)

  case class board(data: Seq[Seq[field]]) {
    val isWin: Boolean = {
      val vert = data.count(b => b.forall(_.hit)) > 0
      val horz = (0 until 5).count(i => data.forall(_ (i).hit)) > 0
      vert || horz
    }

    def update(draw: Int): board = {
      board(data.map(l => l.map(f => if (f.num == draw) field(f.num, true) else f)))
    }

    val score: Long = data.flatten.filterNot(_.hit).map(_.num).sum
  }

  def part1(): Long = {
    var brds = boards.toSeq

    draws
      .flatMap(d => {
        brds = brds.filterNot(_.isWin).map(_.update(d))
        val maybewin = findWin(brds)

        if (maybewin.isDefined)
          Some(maybewin.get.score * d)
        else None
      })
      .head
  }

  def part2(): Long = {
    var brds = boards.toSeq

    draws
      .flatMap(d => {
        brds = brds.filterNot(_.isWin).map(_.update(d))
        val maybewin = findWin(brds)

        if (maybewin.isDefined)
          Some(maybewin.get.score * d)
        else None
      })
      .last
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
