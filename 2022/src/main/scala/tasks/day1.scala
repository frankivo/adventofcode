package com.github.frankivo
package tasks

object day1 {
  def main(args: Array[String]): Unit = {
    val part1 = calculateMaxCalories()
    val part2 = calculateSumOfTop3()

    println(s"Richest elf: ${part1.maxSum}")
    println(s"Sum of top3 elfs: ${part2}")
  }

  case class ResultPart1(currentElfSum: Int, maxSum: Int)

  case class ResultPart2(currentElfSum: Int, maxSums: Seq[Int])

  // Part 1
  private def calculateMaxCalories(): ResultPart1 = {
    input.foldLeft(ResultPart1(0, 0)) {
      (res, it) => {
        if (!it.isBlank) {
          val curSum = res.currentElfSum + it.toInt
          val maxSum = if (curSum > res.maxSum) curSum else res.maxSum

          ResultPart1(curSum, maxSum)
        } else {
          ResultPart1(0, res.maxSum)
        }
      }
    }
  }

  // Part 2
  private def calculateSumOfTop3() : Long = {
    val top3  = input.foldLeft(ResultPart2(0, Seq())) {
      (res, it) => {
        if (!it.isBlank) {
          val curSum = res.currentElfSum + it.toInt
          val maxSums = res.maxSums :+ curSum

          ResultPart2(curSum, maxSums.sorted.reverse.take(3))
        } else {
          ResultPart2(0, res.maxSums)
        }
      }
    }

    top3.maxSums.sum
  }

  private val input: Seq[String] = util.get(1)
}
