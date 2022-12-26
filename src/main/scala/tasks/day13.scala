package com.github.frankivo
package tasks

object day13 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of sorted pair indices: $part1")
  }

  type Packet = Seq[Any]

  private case class Pair(index: Int, left: Packet, right: Packet)

  def part1: Long = rawInput.flatMap(p => Option.when(compareLists(p.left, p.right))(p.index)).sum

  private def compareLists(left: Packet, right: Packet): Boolean = {
    left.length == right.length
  }

  private val rawInput: Seq[Pair] = util.get("day13.txt")
    .filterNot(_.isEmpty)
    .map(Parser().parse(_))
    .sliding(2)
    .zip(LazyList.from(1))
    .map(p => Pair(p._2, p._1.head, p._1.last))
    .toSeq

  private class Parser {
    private var toParse: String = ""

    def parse(str: String): Packet = {
      toParse = str
      parseList
    }

    private def parseList: Packet = {
      toParse = toParse.drop(1)
      Seq()
    }
  }

}
