object day2 {
  def main(args: Array[String]): Unit = {
    var pos = 0
    var depth = 0


    println(readFile())
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
