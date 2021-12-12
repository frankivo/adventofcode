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
          val newBorn = cur ++ Seq.fill(cur.count(_ == 0))(0)
          val countDown = newBorn.map(_ - 1)
          val x = countDown.map(f => if (f == -1) 8 else f)
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
