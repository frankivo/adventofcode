package com.github.frankivo
package tasks

import com.google.gson.{JsonArray, JsonElement, JsonParser}

import scala.jdk.CollectionConverters.*

object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of sorted pair indices: $part1")
  }

  type Packet = Map[Int, JsonElement]

  private case class Pair(index: Int, left: Packet, right: Packet)

  def part1: Long = rawInput.flatMap(p => Option.when(compareLists(p.left, p.right))(p.index)).sum

  private def compareLists(left: Packet, right: Packet): Boolean = {
    left.forall(l => {
      val r = right.get(l._1)
      if (r.isEmpty)
        true
      else if (l._2.isJsonPrimitive && r.get.isJsonPrimitive)
        compareItems(l._2, r.get)
      else {
        compareLists(l._2.asPacket, r.get.asPacket)
      }
    })
  }

  private def compareItems(left: JsonElement, right: JsonElement): Boolean = left.getAsInt <= right.getAsInt

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
  }

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(JsonParser.parseString)
    .map(_.getAsJsonArray)
    .map(_.asPacket)
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}
