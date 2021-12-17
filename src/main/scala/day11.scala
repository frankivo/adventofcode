object day11 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    //    println(s"Part 2: ${part2()}")
  }

  type board = Seq[Seq[Int]]

  def part1(): Int = {
    val data = input
    data.debug()
    val x = data.grow
    println("*" * 10)
    x.debug()

    0
  }

  extension (b: board) {
    def grow: board = b.map(_.map(_ + 1))
    
    def debug(): Unit = {
      b.map(_.map(_.toString).map(_.replace("10", "X")).mkString)
        .foreach(println)
    }
  }

  def part2(): Int = ???

  val input: Seq[Seq[Int]] = {
    io.Source.fromResource("day11.txt").getLines().map(l => l.toCharArray.map(_.getNumericValue).toSeq).toSeq
  }
}
