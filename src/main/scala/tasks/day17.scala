package com.github.frankivo
package tasks

import java.time.Instant


object day17 {
  def main(args: Array[String]): Unit = {
    println(s"Tower is ${part1()} units tall")
    //    println(s"Tower is ${part2()} units tall")
  }

  private val jetStream = JetStream()
  private val rockStream = RockStream()

  private val moveCount = 2022
  private val fieldWidth = 7
  private val rockStatic = "#"
  private val rockMoving = "@"
  private val air = "."
  private val bits = (0 until 7).map(i => (i, 1 << i)).toMap

  private def part1(): Int = solve(Map.empty[Int, Int], moveCount).size

  private def part2(): Long = {
    //    val p1 = solve(Set.empty[coordinate], 200)
    //    p1.show()
    0
  }

  private def solve(start: Map[Int, Int], moves: Int): Map[Int, Int] = {
    (0 until moves).foldLeft(start) {
      (field, _) => {
        val stopped = Seq.unfold(rockStream.next(field.height)) {
          r => {
            Option.when(r.exists(_.name == rockMoving)) {
              val moved = r.blowJet(field)
              val fallen = moved.fall(field)
              (fallen, fallen)
            }
          }
        }.last

        stopped.foldLeft(field) {
          (fieldState, cur) => fieldState + (cur.y -> (fieldState.getOrElse(cur.y, 0) | bits(cur.x)))
        }
      }
    }
  }

  extension (field: Map[Int, Int]) {
    private def height: Int = field.size

    private def bitmask(y: Int): Int = field.getOrElse(y, 0)

    private def show(): Unit = {
      (-1 until height).reverse.foreach(y => {
        (-1 to fieldWidth).foreach(x => {
          if (y == -1)
            print(if (x == -1 || x == fieldWidth) "+" else "-")
          else if (x == -1 || x == fieldWidth)
            print("|")
          else
            print(if ((bitmask(y) & bits(x)) == bits(x)) rockStatic else air)
        })
        println()
      })
      println()
    }
  }

  extension (rock: Seq[coordinate]) {
    private def blowJet(field: Map[Int, Int]): Seq[coordinate] = {
      val jet = jetStream.next
      rock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlockedHorizontal(jet, field)) 0 else 1
          coordinate(r.x + (direction * blocked), r.y, r.name)
        })
    }

    private def fall(field: Map[Int, Int]): Seq[coordinate] = {
      rock
        .map(r => {
          coordinate(
            r.x,
            r.y - (if (isBlockedVertical(field)) 0 else 1),
            if (isBlockedVertical(field)) rockStatic else rockMoving
          )
        })
    }

    private def isBlockedHorizontal(jet: Char, field: Map[Int, Int]): Boolean = {
      val move = if (jet == '>') 1 else -1
      rock.exists(r =>
        Seq(-1, fieldWidth).contains(r.x + move) ||
          (field.bitmask(r.y) & bits(r.x + move)) == bits(r.x + move)
      )
    }

    private def isBlockedVertical(field: Map[Int, Int]): Boolean = {
      rock.exists(r => {
        r.y - 1 == -1 ||
          (field.bitmask(r.y - 1) & bits(r.x)) == bits(r.x)
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
      val xys = shape match
        case 0 => // Horizontal line
          (2 to 5).map(i => (i, top + 3))
        case 1 => // Cross
          Seq((3, top + 5), (2, top + 4), (3, top + 4), (4, top + 4), (3, top + 3))
        case 2 => // Reverse L
          Seq((4, top + 5), (4, top + 4), (4, top + 3), (3, top + 3), (2, top + 3))
        case 3 => // Vertical line
          (3 to 6).map(i => (2, top + i))
        case 4 => // Square
          Seq((2, top + 3), (3, top + 3), (2, top + 4), (3, top + 4))

      xys.map(xy => coordinate(xy._1, xy._2, rockMoving))
    }
  }
}
