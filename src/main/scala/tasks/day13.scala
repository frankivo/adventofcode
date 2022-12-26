package com.github.frankivo
package tasks

import com.google.gson.{JsonArray, JsonElement, JsonParser}

import scala.jdk.CollectionConverters.*

object day13 {
  def main(args: Array[String]): Unit = {
    //    println(s"Sum of sorted pair indices: $part1")

    println(1 compare 0)
    println(0 compare 0)
    println(1 compare 1)

  }

  type Packet = Map[Int, JsonElement]

  private case class Pair(index: Int, left: Packet, right: Packet)

  def part1: Long = {
    val i = input
    //      .filter(_.index == 1)

    val indices = i
      .flatMap(p => Option.when(compareLists(p.left, p.right) > 0)(p.index))
    println(indices)
    indices.sum
  }

  private def compareLists(left: Packet, right: Packet): Int = {
    left.map(l => {
      val r = right.get(l._1)
      if (r.isEmpty)
        -1
      else if (!l._2.isJsonArray && !r.get.isJsonArray)
        l._2.compare(r.get)
      else
        compareLists(l._2.asPacket, r.get.asPacket)
    })
      .find(_ != 0).getOrElse(right.size.compare(left.size))
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

  private val input: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(JsonParser.parseString)
    .map(_.getAsJsonArray)
    .map(_.asPacket)
    .grouped(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}
