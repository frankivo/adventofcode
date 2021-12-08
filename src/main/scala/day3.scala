object day3 {
  def main(args: Array[String]): Unit = {
    println(getData.length)
  }

  def getData: Iterator[String] = scala.io.Source.fromResource("day3.txt").getLines()
}
