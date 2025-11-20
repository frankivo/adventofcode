package com.github.frankivo
package tasks

import scala.util.Try

object day10 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of six signal strenghts: $getSignalStrenghts")
    crt()
  }

  private def getSignalStrenghts: Long = {
    val cycles = Seq(20, 60, 100, 140, 180, 220)

    cycle
      .filter(cycles contains _._1)
      .map(c => c._1 * c._2)
      .sum
  }

  private def crt(): Unit = {
    cycle.foreach(c => {
      val centre = c._2 + 1
      val sprite = Seq(centre - 1, centre, centre + 1).map(_ % 40)
      print(if (sprite.contains(c._1 % 40)) '#' else '.')
      if (c._1 % 40 == 0) println
    })
  }

  private def cycle: Seq[(Int, Int)] = {
    val start = Seq((1, 1))
    input.zip(LazyList.from(2)).foldLeft(start) {
      (state, current) => state :+ (current._2, state.last._2 + current._1.param.getOrElse(0))
    }
  }

  private case class command(cmd: String, param: Option[Int] = None)

  private val input: Seq[command] = util.get(10)
    .map(_.split("""\s"""))
    .map(c => command(c.head, Try(c.last.toInt).toOption))
    .flatMap(c => {
      c.cmd match
        case "noop" => Seq(c)
        case "addx" => Seq(command("noop"), c)
    })
}
