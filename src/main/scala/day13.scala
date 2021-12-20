object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  def part1(): Int = {
    coordinates.take(5).foreach(println)
    folds.foreach(println)
    0
  }

  def part2(): Int = 0

  val data: Seq[String] = io.Source.fromResource("day13.txt").getLines().toSeq

  val coordinates: Seq[(Int, Int)] = {
    data
      .filter(_.contains(','))
      .map(_.split(','))
      .map(c => (c.head.toInt, c.last.toInt))
  }

  val folds: Seq[(String, Int)] = {
    data
      .filter(_.contains('='))
      .map(_.split(' '))
      .map(_.last)
      .map(_.split('='))
      .map(c => (c.head, c.last.toInt))
  }
}
