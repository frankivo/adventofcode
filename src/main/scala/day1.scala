object day1 {

  @main def main(): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
  }

  def part1(): Int = {
    getData
      .sliding(2, 1)
      .count(x => x(0) < x(1))
  }

  def part2(): Int = {
    getData
      .sliding(3, 1)
      .map(_.sum)
      .sliding(2, 1)
      .count(x => x(0) < x(1))
  }

  def getData: Iterator[Int] = {
    scala.io.Source.fromResource("day1.txt")
      .getLines()
      .map(_.toInt)
  }
}
