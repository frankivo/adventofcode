package com.github.frankivo
package tasks


object day17 {
  def main(args: Array[String]): Unit = {
    part1()
  }

  private val jetStream = JetStream()
  private val rockStream = RockStream()

  private val moveCount = 2022
  private val fieldWidth = 7
  private val rockStatic = "#"
  private val rockMoving = "@"

  private def part1(): Unit = {
    val start = Set.empty[coordinate]

    val end = (0 until 2).foldLeft(start) {
      (state1, _) => {
        val f = state1.addRock()
        f.show()
        Seq.unfold(f) {
          field => {
            val rock = field.getRock
            Option.when(rock.nonEmpty) {
              val movedRock = field.moveByJet
              val movedField = field.diff(rock) ++ movedRock
              movedField.show()
              println("Fall")
              val fallen = movedField.fall

              val fallenField = movedField.diff(movedField.getRock) ++ fallen
              fallenField.show()
              (fallenField, fallenField)
            }
          }
        }.last
      }
    }

    end.show()
    println(end)
  }

  extension (coordinates: Set[coordinate]) {
    private def height: Int = coordinates.maxByOption(_.y).map(_.y).getOrElse(0)

    private def addRock(): Set[coordinate] = coordinates ++ rockStream.next(height)

    private def show(): Unit = {
      (0 to height).reverse.foreach(y => {
        (0 to fieldWidth + 1).foreach(x => {
          if (y == 0)
            print(if (x == 0 || x == fieldWidth + 1) "+" else "-")
          else if (x == 0 || x == fieldWidth + 1)
            print("|")
          else
            print(coordinates.find(c => c.y == y && c.x == x).map(_.name).getOrElse("."))
        })
        println()
      })
      println()
    }

    private def getRock: Set[coordinate] = coordinates.filter(_.name == rockMoving)

    private def moveByJet: Set[coordinate] = {
      val jet = jetStream.next
      println(s"Move $jet")

      getRock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlockedHorizontal(jet)) 0 else 1
          coordinate(r.x + (direction * blocked), r.y, r.name)
        })
    }

    private def fall: Set[coordinate] = {
      getRock
        .map(r => {
          coordinate(
            r.x,
            r.y - (if (isBlockedVertical) 0 else 1),
            if (isBlockedVertical) rockStatic else rockMoving
          )
        })
    }

    private def isBlockedHorizontal(jet: Char): Boolean = {
      // TODO: check every Y in shape
      // TODO: check against other rocks
      jet match {
        case '>' =>
          getRock.maxBy(_.x).x == 7
        case '<' =>
          getRock.minBy(_.x).x == 0
      }
    }

    private def isBlockedVertical: Boolean = {
      // TODO: check against other rocks in every X
      val btm = getRock.minBy(_.y) // Bottom of the rock

      btm.y - 1 == 0 // Floor
        || coordinates.find(c => c.x == btm.x && c.y == btm.y - 1).map(_.name).getOrElse(".") == rockStatic // Rock
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
          (3 to 6).map(i => coordinate(i, top + 4, rockMoving))
        case 1 => // Cross
          Seq(
            coordinate(4, top + 6, rockMoving), // Top
            coordinate(3, top + 5, rockMoving),
            coordinate(4, top + 5, rockMoving),
            coordinate(5, top + 5, rockMoving),
            coordinate(4, top + 4, rockMoving), // Bottom
          )
    }
  }
}
