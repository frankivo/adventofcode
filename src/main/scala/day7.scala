object day7 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
  }

  def part1(): Int = {
    val dest = median(crabs)
    crabs
      .map(c => (dest - c).abs)
      .sum
  }

  def part2(): Int = {
    val dest = average(crabs)
    crabs
      .map(c => (dest - c).abs)
      .map(s => (1 to s).sum)
      .sum
  }

  def average(data: Seq[Int]): Int = data.sum / data.length

  def median(data: Seq[Int]): Int = {
    val sorted = data.sorted
    val isEven = data.length % 2 == 0

    if (isEven) {
      val drop = (data.length - 2) / 2
      sorted.slice(drop, drop + 2).sum / 2
    }
    else {
      val drop = ((data.length - 2) / 2) + 1
      sorted.slice(drop, drop + 1).head
    }
  }

  val crabs: Seq[Int] =
    scala.io.Source.fromResource("day7.txt").getLines().next().split(",").map(_.toInt)
}
