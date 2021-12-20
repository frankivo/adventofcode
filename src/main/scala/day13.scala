object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")

    println(s"Part 2:")
    part2()
  }

  def part1(): Int = coordinates.foldLine(folds.head).size

  def part2(): Unit = {
    folds
      .foldLeft(coordinates) { (paper, cur) => paper.foldLine(cur) }
      .debug()
  }

  case class Coordinate(col: Int, row: Int)

  type Fold = (Char, Int)

  type Paper = Set[Coordinate]

  extension (paper: Paper) {
    def width: Int = paper.map(_.col).max

    def height: Int = paper.map(_.row).max

    def debug(fold: Option[Fold] = None): Unit = {
      (0 to height).map(row =>
        (0 to width).map(col => {
          if (fold.isDefined && fold.get._1 == 'y' && fold.get._2 == row) '-'
          else if (fold.isDefined && fold.get._1 == 'x' && fold.get._2 == col) '|'
          else if (paper.contains(Coordinate(col, row))) '#'
          else '.'
        })
      ).foreach(x => println(x.mkString))
    }

    def foldLine(fold: Fold): Paper = {
      paper.map(c => {
        if (fold._1 == 'y' && c.row > fold._2)
          Coordinate(c.col, fold._2 - (c.row - fold._2))
        else if (fold._1 == 'x' && c.col > fold._2)
          Coordinate(fold._2 - (c.col - fold._2), c.row)
        else
          Coordinate(c.col, c.row)
      })
    }
  }

  lazy val coordinates: Paper = {
    data
      .filter(_.contains(','))
      .map(_.split(','))
      .map(c => Coordinate(c.head.toInt, c.last.toInt))
      .toSet
  }

  lazy val folds: Seq[Fold] = {
    data
      .filter(_.contains('='))
      .map(_.split(' '))
      .map(_.last)
      .map(_.split('='))
      .map(c => (c.head.head, c.last.toInt))
  }

  lazy val data: Seq[String] = io.Source.fromResource("day13.txt").getLines().toSeq
}
