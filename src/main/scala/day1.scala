object day1 {

  @main def main() : Unit = {
    Seq(scala.io.Source.fromResource("day1.txt")
      .getLines()
      .map(_.toInt)
      .sliding(2, 1)
      .count(x => x(0) < x(1))
    )      .foreach(println)
  }

}
