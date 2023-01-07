package com.github.frankivo
package tasks


object day17 {
  def main(args: Array[String]): Unit = {
    part1()

  }

  private val jetStream = JetStream()
  private val rockStream = RockStream()

  private val moveCount: Int = 2022

  private def part1(): Unit = {
    val start = Set.empty[coordinate]

    val end = (0 until 1).foldLeft(start) {
      (state1, _) => {
        Seq.unfold(state1.addRock()) {
          state2 => {
            val rock = state2.moveByJet


            None
          }
        }.last
      }
    }

    end.show()
    println(end)
  }

  extension (field: Set[coordinate]) {
    private def height: Int = field.maxByOption(_.y).map(_.y).getOrElse(0)

    private def addRock(): Set[coordinate] = field ++ rockStream.next(height)

    private def show(): Unit = {
      (0 to height).reverse.foreach(y => {
        (0 to 7).foreach(x => {
          print(field.find(c => c.y == y && c.x == x).map(_.name).getOrElse("."))
        })
        println()
      })
    }

    private def getRock: Set[coordinate] = field.filter(_.name == "@")

    private def isBlocked(jet: Char): Boolean = {
      // TODO: check every Y in shape
      // TODO: check against other rocks
      jet match {
        case '>' =>
          getRock.maxBy(_.x).x == 7
        case '<' =>
          getRock.minBy(_.x).x == 0
      }
    }

    private def moveByJet: Set[coordinate] = {
      val jet = jetStream.next
      getRock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlocked(jet)) 0 else 1
          coordinate(r.x + direction * blocked, r.y, r.name)
        })
    }
  }

  private class JetStream {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)
  }

  private class RockStream {
    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next(top: Int): Seq[coordinate] = {
      val shape = iterator.next() % 5
      shape match
        case 0 => // Horizontal line
          (2 to 5).map(i => coordinate(i, top + 3, "@"))
    }
  }
}
