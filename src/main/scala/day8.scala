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
    val in = panels.map(_._1).toSeq

    val data = in.map(l => {
      val step0 = Seq(1, 4, 7, 8)
        .flatMap(x => l.find(_.length == signalCount(x)._2).map(y => x -> y))
        .toMap

      val step1 = step0 +
        (9 -> findInOthers(l, 6, step0(4)).get)

      val step2 = step1 +
        (3 -> findInOthers(remaining(l, step1), 5, step0(1)).get) +
        (0 -> findInOthers(remaining(l, step1), 6, step0(1)).get)

      val step3 = step2 +
        (6 -> remaining(l, step2).find(_.length == 6).get)

      val step4 = step3 +
        (5 -> remaining(l, step3).find(r => step3(6).diff(r).length == 1).get)

      val step5 = step4 +
        (2 -> remaining(l, step4).head)

      debug(step5)
      println(remaining(l, step5))

      0
    })

    0
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

  def debug(data: Map[Int, String]): Unit = {
    data
      .toSeq
      .sortBy(_._1)
      .map(x => s"${x._1} -> ${x._2}")
      .foreach(println)
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
