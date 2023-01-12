package com.github.frankivo

import scala.io.Source

object util {
  def get(filename: String): Seq[String] = {
    val dir = if (sys.env.getOrElse("DEMO", "0").toInt == 1) "demo" else "input"
    val path = s"$dir/$filename"
    Source.fromResource(path).getLines().toSeq
  }

  def clamp(i: Int, lower: Int, upper: Int) : Int = lower max i min upper
}
