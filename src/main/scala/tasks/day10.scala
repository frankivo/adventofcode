package com.github.frankivo
package tasks

import scala.util.Try

object day10 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    println(input)
  }

  private case class command(cmd: String, param: Option[Int] = None)

  private val input: Seq[command] = util.get("day10.txt")
    .map(_.split("""\s"""))
    .map(c => command(c.head, Try(c.last.toInt).toOption))
    .flatMap(c => {
      c.cmd match
        case "noop" => Seq(c)
        case "addx" => Seq(command("noop"), command("noop"), c)
    })
}
