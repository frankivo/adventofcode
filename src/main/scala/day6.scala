object day6 {
  def main(args: Array[String]): Unit = {
    println(s"Part1: ${bruteForce(80)}")
    println(s"Part2: ${countFish(256)}")
  }

  def bruteForce(days: Int): Long = {
    (0 until days)
      .foldLeft(lanternfish) {
        (cur, _) => {
          val countDown = cur.map(_ - 1)
          (countDown ++ Seq.fill(countDown.count(_ == -1))(8))
            .map(f => if (f == -1) 6 else f)
        }
      }
      .length
  }

  def countFish(days: Int): Long = {
    val init: Seq[Long] = (0 to 8).map(i => lanternfish.count(_ == i))

    (0 until days)
      .foldLeft(init) {
        (cur, _) => {
          cur.slice(1, 7) ++
          Seq(
            cur(7) + cur.head,
            cur(8),
            cur.head
          )
        }
      }
      .sum
  }

  def lanternfish: Seq[Int] = {
    scala.io.Source.fromResource("day6.txt")
      .getLines()
      .next()
      .split(",")
      .map(_.toInt)
  }
}
