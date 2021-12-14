object day8 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
    println(s"Part2: ${part2()}")
  }

  def part1(): Int = {
    val lengths = Seq(2, 3, 4, 7)

    panels
      .map(_._2)
      .map(s => s.filter(p => lengths.contains(p.length)))
      .map(_.length)
      .sum
  }

  def part2(): Int = {
    panels
      .map(l => {
        val mixed = mixedSignals(l._1)
        l._2
          .map(_.sorted)
          .flatMap(s => mixed.find(_._2 == s))
          .map(_._1)
          .mkString
          .toInt
      })
      .sum
  }

  def mixedSignals(data: Seq[String]): Map[Int, String] = {
    val step0 = Seq(1, 4, 7, 8)
      .flatMap(x => data.find(_.length == signalCount(x)._2).map(y => x -> y))
      .toMap

    val step1 = step0 +
      (9 -> findInOthers(data, 6, step0(4)).get)

    val step2 = step1 +
      (3 -> findInOthers(remaining(data, step1), 5, step0(1)).get) +
      (0 -> findInOthers(remaining(data, step1), 6, step0(1)).get)

    val step3 = step2 +
      (6 -> remaining(data, step2).find(_.length == 6).get)

    val step4 = step3 +
      (5 -> remaining(data, step3).find(r => step3(6).diff(r).length == 1).get)

    val step5 = step4 +
      (2 -> remaining(data, step4).head)

    step5
      .map(s => s._1 -> s._2.sorted)
  }

  def findInOthers(data: Seq[String], size: Int, other: String): Option[String] = {
    data
      .filter(_.length == size)
      .map(x => (x, x.intersect(other)))
      .filter(_._2.length == other.length)
      .map(_._1)
      .headOption
  }

  def remaining(data: Seq[String], found: Map[Int, String]): Seq[String] = {
    data.filterNot(d => found.values.toSeq.contains(d))
  }

  val signals: Seq[(String, Int)] = Seq(
    "abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"
  ).zipWithIndex

  val signalCount: Seq[(Int, Int)] = // Signal, panel count
    signals.map(s => (s._2, s._1.length))

  def panels: Iterator[(Seq[String], Seq[String])] = {
    input
      .map(l => l.split("""\|"""))
      .map(l => (l.head.trim, l.last.trim))
      .map(l => (l._1.split(" ").toSeq, l._2.split(" ").toSeq))
  }

  def input: Iterator[String] = scala.io.Source.fromResource("day8.txt").getLines()
}
