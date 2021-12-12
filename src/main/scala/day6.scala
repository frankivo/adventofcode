object day6 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${part1()}")
  }

  def part1(): Long = {
    val fish = lanternfish
    println(s"Initial state: ${fish.mkString(",")}")

    (0 until 18)
      .foldLeft(fish) {
        (cur, step) => {
          val countDown = cur.map(_ - 1)
          val newBorn = countDown ++ Seq.fill(countDown.count(_ == 0))(8)
          val x = newBorn.map(f => if (f == -1) 6 else f)
          println(s"After ${step + 1} days: ${x.mkString(",")}")
          x
        }
      }
      .length
  }

  def lanternfish: Seq[Int] = {
    scala.io.Source.fromResource("day6.txt")
      .getLines()
      .next()
      .split(",")
      .map(_.toInt)
  }
}
