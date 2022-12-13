package com.github.frankivo
package tasks

import scala.util.Try

object day10 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of six signal strenghts: $getSignalStrenghts")
  }

  private def getSignalStrenghts: Long = {
    val cycles = Seq(20, 60, 100, 140, 180, 220)

    cycle
      .filter(cycles contains _._1)
      .map(c => c._1 * c._2)
      .sum
  }

  private def cycle: Seq[(Int, Int)] = {
    val start = Seq((1, 1))
    input.zip(LazyList.from(2)).foldLeft(start) {
      (state, current) => state :+ (current._2, state.last._2 + current._1.param.getOrElse(0))
    }
  }

  private case class command(cmd: String, param: Option[Int] = None)

  private val input: Seq[command] = util.get("day10.txt")
    .map(_.split("""\s"""))
    .map(c => command(c.head, Try(c.last.toInt).toOption))
    .flatMap(c => {
      c.cmd match
        case "noop" => Seq(c)
        case "addx" => Seq(command("noop"), c)
    })
}
