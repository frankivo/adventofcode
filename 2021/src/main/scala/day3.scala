import scala.annotation.tailrec

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

  def oxygen: Int = toInt(reduce(getData.toSeq, '1').head)

  def scrubbber: Int = toInt(reduce(getData.toSeq, '0').head)

  def toInt(bin: String): Int = Integer.parseInt(bin, 2)

  @tailrec
  def reduce(data: Seq[String], char: Char, step: Int = 0): Seq[String] = {
    if (data.length == 1) return data

    val multiplier = if (char == '1') 1 else -1

    val grouped = data
      .groupBy(r => r.charAt(step))

    reduce(
      if (grouped.head._2.length == grouped.last._2.length)
        grouped.filter(_._1 == char).head._2
      else
        grouped.max((a, b) => a._2.length.compare(b._2.length) * multiplier)._2,
      char,
      step + 1
    )
  }
}
