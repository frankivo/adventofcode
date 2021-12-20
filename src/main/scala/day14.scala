object day14 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    //    println(s"Part 2: ${part2()}")
  }

  def part1(): Int = {
    val replaced = (0 until 10)
      .foldLeft(template) {
        (start, ins) => {
          doInsertions(start)
        }
      }

    val occurrences = replaced
      .groupBy(identity)
      .view.mapValues(_.length)

    occurrences.max((a, b) => a._2.compare(b._2))._2 -
      occurrences.min((a, b) => a._2.compare(b._2))._2
  }

  def doInsertions(start: String): String = {
    val replacements = start
      .sliding(2)
      .map(p => {
        val ins = insertions.find(i => i._1 == p)
        if (ins.isDefined)
          p.replaceAll(ins.get._1, ins.get._2)
        else
          p
      })
      .toSeq
    (replacements.dropRight(1).map(_.take(2)) ++ replacements.takeRight(1))
      .mkString
  }

  def part2(): Int = ???

  lazy val data: Seq[String] = io.Source.fromResource("day14.txt").getLines().toSeq

  lazy val template: String = data.head

  lazy val insertions: Map[String, String] = {
    data
      .drop(2)
      .map(_.split("->"))
      .map(l => (l.head.trim, l.last.trim))
      .map(l => (l._1, l._1.head + l._2 + l._1.last))
      .toMap
  }
}
