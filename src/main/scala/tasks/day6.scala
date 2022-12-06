package com.github.frankivo
package tasks

object day6 {
  def main(args: Array[String]): Unit = {
    showMarkers()
  }

  def showMarkers(): Unit = input.map(marker).foreach(println)

  def marker(msg: String): Int = msg
    .sliding(4, 1)
    .zipWithIndex
    .find(_._1.distinct.length == 4)
    .map(_._2)
    .get + 4

  private val input: Seq[String] = Seq(
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
    "bvwbjplbgvbhsrlpgdmjqwftvncz",
    "nppdvjthqldpwncqszvftbrmjlhg",
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",
  )
}
