object day3 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
  }

  private def getData: Iterator[String] = scala.io.Source.fromResource("day3.txt").getLines()

  private val IS_SIGNIFICANT = getData.length / 2
  private val BIT_COUNT = getData.next().length

  def part1(): Long = {
    val significant = getData
      .map(_.toCharArray)
      .foldLeft(Seq.fill(BIT_COUNT)(0)) {
        (sum, cur) => {
          sum
            .indices
            .map(i => sum(i) + cur(i).compare('0'))
        }
      }

    val gamma = significant
      .map(s => if (s > IS_SIGNIFICANT) '1' else '0')
      .mkString
    val epsilon = gamma.map(c => if (c == '1') '0' else '1').mkString

    Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
  }
}
