object day3 {
  def main(args: Array[String]): Unit = {
    val size = getData.length

    val significant = getData
      .map(_.toCharArray)
      .foldLeft(0) { (sum, i) => sum + i(0).compare('0') }

    val gammaRate1 = getSig(significant, size)
    println(gammaRate1)
  }

  def getSig(num: Long, size: Long): Char = if (num > (size / 2)) '1' else '0'

  def getData: Iterator[String] = scala.io.Source.fromResource("day3.txt").getLines()
}
