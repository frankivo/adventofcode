object day1 {

  @main def main(): Unit = {
    println(day1())
  }

  def day1(): Int = {
    scala.io.Source.fromResource("day1.txt")
      .getLines()
      .map(_.toInt)
      .sliding(2, 1)
      .count(x => x(0) < x(1))
  }

}
