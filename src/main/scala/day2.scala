object day2 {
  def main(args: Array[String]): Unit = {
    println(s"Day2: ${part1()}")
  }

  case class command(action: String, positions: Int)

  case class coordinates(pos: Int, depth: Int)

  def part1(): Long = {
    val finalCoordinates = readFile()
      .foldLeft(coordinates(0, 0)) {
        (cur, cmd) => {
          cmd.action match {
            case "forward" => coordinates(cur.pos + cmd.positions, cur.depth)
            case "down" => coordinates(cur.pos - cmd.positions, cur.depth)
            case "up" => coordinates(cur.pos, cur.depth - cmd.positions)
          }
        }
      }

    finalCoordinates.depth * finalCoordinates.pos
  }

  def readFile(): Iterator[command] = {
    scala.io.Source.fromResource("day2.txt")
      .getLines()
      .map(l => {
        val data = l.split(" ")
        command(data(0), data(1).toInt)
      })
  }
}
