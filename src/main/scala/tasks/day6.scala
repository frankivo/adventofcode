package com.github.frankivo
package tasks

object day6 {
  def main(args: Array[String]): Unit = {
    println(s"Marker is at pos $getMarker")
  }

  def getMarker: Int = marker(util.get("day6.txt").head)

  def marker(msg: String): Int = msg
    .sliding(4, 1)
    .zipWithIndex
    .find(_._1.distinct.length == 4)
    .map(_._2)
    .get + 4
}
