package com.github.frankivo
package tasks

import com.google.gson.{JsonArray, JsonElement, JsonParser}

import scala.jdk.CollectionConverters.*
import scala.util.Try

object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of sorted pair indices: $part1")
    println(s"Sum of divider indices: $part2")

  }

  type Packet = Map[Int, JsonElement]

  private case class Pair(index: Int, left: Packet, right: Packet)

  def part1: Long = {
    input
      .flatMap(p => Option.when(p.left.compare(p.right) < 0)(p.index))
      .sum
  }

  def part2: Long = {
    val mapped = (lines ++ Seq("[[2]]", "[[6]]")).map(asInt)
    val firstDivider = mapped.count(_ < 2) + 1
    val secondDivider = mapped.count(_ < 6) + 1
    firstDivider * secondDivider
  }

  extension (string: String) {
    def asInt: Int = """([0-9]+|\[])""".r.findFirstIn(string).get.toIntOption.getOrElse(0)
  }

  extension (array: JsonArray) {
    def asPacket: Packet = array.asList().asScala.zipWithIndex.map(i => (i._2, i._1)).toMap
  }

  extension (element: JsonElement) {
    def asPacket: Packet = {
      if (element.isJsonArray)
        element.getAsJsonArray.asPacket
      else
        JsonParser.parseString(s"[$element]").getAsJsonArray.asPacket
    }

    def compare(other: JsonElement): Int = element.getAsInt.compare(other.getAsInt)
  }

  extension (packet: Packet) {
    def compare(other: Packet): Int = {
      packet.map(l => {
        val r = other.get(l._1)
        if (r.isEmpty)
          1
        else if (!l._2.isJsonArray && !r.get.isJsonArray)
          l._2.compare(r.get)
        else
          l._2.asPacket.compare(r.get.asPacket)
      })
        .find(_ != 0).getOrElse(packet.size.compare(other.size))
    }
  }

  private val input: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(JsonParser.parseString)
    .map(_.getAsJsonArray)
    .map(_.asPacket)
    .grouped(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq

  private val lines: Seq[String] = util.get("day13.txt")
    .filterNot(_.isEmpty)
}
