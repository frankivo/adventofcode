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
      // Assume no list and equal size
      compareItems(l._2, right(l._1))
    })
  }

  private def compareItems(left: JsonElement, right: JsonElement): Boolean = left.getAsInt <= right.getAsInt

  extension (array: JsonArray) {
    def asMap: Map[Int, JsonElement] = array.asList().asScala.zipWithIndex.map(i => (i._2, i._1)).toMap
  }

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(JsonParser.parseString)
    .map(_.getAsJsonArray)
    .map(_.asMap)
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq
}
