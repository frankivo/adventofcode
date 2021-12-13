import day8.findSegment

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

    println(signalCount)
    val data = in.map(l => {
      val step0 = Seq(1, 4, 7, 8)
        .map(i => (i, findSegment(l, i).toCharArray))
        .toMap

      val step1 = step0 +
        (9 -> findInOthers(l, 6, step0(4)))

      debug(step1)
      0
    })

    0
  }

  def findInOthers(data: Seq[String], size: Int, other: Array[Char]): Array[Char] = {
    data
      .filter(_.length == size)
      .map(x => (x, x.toCharArray.intersect(other)))
      .filter(_._2.length == other.length)
      .head
      ._1.toCharArray
  }

  def debug(data: Map[Int, Array[Char]]): Unit = {
    data
      .toSeq
      .sortBy(_._1)
      .map(x=> s"${x._1} -> ${x._2.mkString(",")}").foreach(println)

  }

  def findSegment(data: Seq[String], num: Int): String =
    data.find(_.length == signalCount.find(_._1 == num).get._2).get

  val signals: Seq[(Seq[Char], Int)] = Seq(
    "abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"
  ).map(_.toCharArray.toSeq).zipWithIndex

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
