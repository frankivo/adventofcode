object day2 {
  def main(args: Array[String]): Unit = {
    println(s"Day2: ${part1()}")
  }

  def part1(): Long = {
    var pos = 0
    var depth = 0

    readFile()
      .foreach(c => {
        c.action match {
          case "forward" => pos += c.positions
          case "down" => depth += c.positions
          case "up" => depth -= c.positions
        }
      })

    pos * depth
  }

  case class command(action: String, positions: Int)

  def readFile(): Seq[command] = {
    scala.io.Source.fromResource("day2.txt")
      .getLines()
      .map(l => {
        val data = l.split(" ")
        command(data(0), data(1).toInt)
      })
      .toSeq
  }
}
