package com.github.frankivo.tasks

import com.github.frankivo.util

object day1 {
  def main(args: Array[String]): Unit = {
    val res = calculateMaxCalories()
    println(s"Richest elf: ${res.maxSum}")
  }

  case class Result(currentElfSum: Int, maxSum: Int)

  private def calculateMaxCalories(): Result = {
    input.foldLeft(Result(0, 0)) {
      (res, it) => {
        if (!it.isBlank) {
          val curSum = res.currentElfSum + it.toInt
          val maxSum = if (curSum > res.maxSum) curSum else res.maxSum

          Result(curSum, maxSum)
        } else {
          Result(0, res.maxSum)
        }
      }
    }
  }

  private val input: Seq[String] = input.get("day1.txt")
}
