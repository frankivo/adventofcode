object day2 {
  def main(args: Array[String]): Unit = {
    println(s"Part 1: ${part1()}")
    println(s"Part 2: ${part2()}")
  }

  case class command(action: String, positions: Int)

  case class coordinates(pos: Int, depth: Int, aim: Int)

  def part1(): Long = {
    val finalCoordinates = readFile()
      .foldLeft(coordinates(0, 0, 0)) {
        (cur, cmd) => {
          cmd.action match {
            case "forward" => coordinates(cur.pos + cmd.positions, cur.depth, 0)
            case "down" => coordinates(cur.pos - cmd.positions, cur.depth, 0)
            case "up" => coordinates(cur.pos, cur.depth - cmd.positions, 0)
          }
        }
      }

    finalCoordinates.pos * finalCoordinates.depth
  }

  def part2(): Long = {
    val finalCoordinates = readFile()
      .foldLeft(coordinates(0, 0, 0)) {
        (cur, cmd) => {
          cmd.action match {
            case "down" => coordinates(cur.pos, cur.depth, cur.aim + cmd.positions)
            case "up" => coordinates(cur.pos, cur.depth, cur.aim - cmd.positions)
            case "forward" => coordinates(cur.pos + cmd.positions, cur.depth + (cur.aim * cmd.positions), cur.aim)
          }
        }
      }

    finalCoordinates.pos * finalCoordinates.depth
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
