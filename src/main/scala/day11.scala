import collection.mutable.Seq as MSeq
import scala.collection.mutable
import scala.util.Try

object day11 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  type Board = Seq[MSeq[Int]]

  extension (b: Board) {
    def grow: Board = b.map(_.map(_ + 1))

    def reset: Board = b.map(_.map(c => if (c > 9) 0 else c))

    def flash: Int = {
      var keepGoing = true
      val flashed = mutable.HashSet[(Int, Int)]()

      while keepGoing
      do {
        keepGoing = false
        b.indices.foreach(row => {
          b.head.indices.foreach(col => {
            if (b(row)(col) > 9 && !flashed.contains((row, col))) {
              flashed += ((row, col))

              adjacent(row, col)
                .foreach(c => {
                  val cur = findCell(c._1, c._2).get
                  b(c._1)(c._2) = cur + 1
                  if (!flashed.contains(c._1, c._2) && b(c._1)(c._2) > 9)
                    keepGoing = true
                })
            }
          })
        })
      }
      flashed.size
    }

    def adjacent(row: Int, col: Int): Seq[(Int, Int)] = {
      val xs = (-1 to 1).map(_ + row)
      val ys = (-1 to 1).map(_ + col)

      xs
        .flatMap(x => ys.map(y => (x, y)))
        .filterNot(_ == (row, col))
        .filter(c => findCell(c._1, c._2).isDefined)
    }

    def findCell(row: Int, col: Int): Option[Int] = Try(b(row)(col)).toOption

    def debug(): Unit = {
      b.map(row => {
        row.map(c => {
          if (c > 9) 'X'
          else c
        }).mkString
      }).foreach(println)
    }

    def allFlash = b.forall(_.forall(_ == 0))
  }

  def part1(): Int = {
    (1 to 100)
      .foldLeft((input, 0)) {
        (a, step) => {
          val grow = a._1.grow
          val flashed = grow.flash
          (grow.reset, a._2 + flashed)
        }
      }
      ._2
  }

  def part2(): Int = {
    var step = 0
    var data = input
    var foundAt = -1

    while foundAt < 0
    do {
      step += 1

      data = data.grow
      data.flash
      data = data.reset

      if (data.allFlash)
        foundAt = step
    }
    foundAt
  }

  val input: Board = {
    io.Source.fromResource("day11.txt")
      .getLines()
      .map(l => MSeq.from(l.toCharArray.map(_.getNumericValue)))
      .toSeq
  }
}
