object day3 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
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

  def part2(): Long = oxygen * scrubbber

  def oxygen: Int = {
    var data = getData.toSeq
    var step = 0
    while data.length > 2
    do {
      data = reduce1(data, step)
      step += 1
    }
    data = data.filter(_.last == '1')
    Integer.parseInt(data.head, 2)
  }

  def scrubbber: Int = {
    var data = getData.toSeq
    var step = 0
    while data.length > 2
    do {
      data = reduce2(data, step)
      step += 1
      println(data)
    }
    data = data.filter(_.last == '0')

    Integer.parseInt(data.head, 2)
  }

  def reduce1(data: Seq[String], step: Int): Seq[String] = {
    data
      .groupBy(r => r.charAt(step))
      .max((a, b) => a._2.length.compare(b._2.length))
      ._2
  }

  def reduce2(data: Seq[String], step: Int): Seq[String] = {
    data
      .groupBy(r => r.charAt(step))
      .min((a, b) => a._2.length.compare(b._2.length))
      ._2
  }

}
