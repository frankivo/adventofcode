package com.github.frankivo

import scala.io.Source

object input {
  def get(filename: String): Seq[String] = {
    val path = s"input/$filename"
    Source.fromResource(path).getLines().toSeq
  }

}
