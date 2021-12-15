object day10 {
  def main(args: Array[String]): Unit = {
    println(scoringTable)
  }

  val scoringTable : Map[Char, Int] = Map(
    ')' -> 3,
    ']' -> 57,
    '}' -> 1197,
    '>' -> 25137,
  )
}
