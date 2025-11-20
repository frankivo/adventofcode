package com.github.frankivo
package tasks

object day6 {
  def main(args: Array[String]): Unit = {
    println(s"first start-of-packet is at pos: $startOfPacketMarker")
    println(s"first start-of-message is at pos: $startOfMessageMarker")
  }

  def startOfPacketMarker: Int = marker(util.get(6).head, 4)

  def startOfMessageMarker: Int = marker(util.get(6).head, 14)

  def marker(msg: String, size: Int): Int = msg
    .sliding(size, 1)
    .zipWithIndex
    .find(_._1.distinct.length == size)
    .map(_._2)
    .get + size
}
