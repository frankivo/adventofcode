package com.github.frankivo

import scala.io.Source

object util {
  def get(filename: String): Seq[String] = {
    val dir = if (sys.env.contains("DEMO")) "demo" else "input"
    val path = s"$dir/$filename"
    Source.fromResource(path).getLines().toSeq
  }

}
