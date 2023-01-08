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
  private val rockStatic = '#'
  private val rockMoving = '@'
  private val air = '.'
  private val bits = (0 until 7).map(i => (i, 1 << i)).toMap

  private type Field = Map[Long, Int]
  private type Rock = Seq[rockElement]

  private def part1(): Int = solve(Map.empty[Long, Int], moveCount).size

  private def part2(): Long = {
    0
  }

  private def solve(start: Field, moves: Long): Field = {
    (0L until moves).foldLeft(start) {
      (field, _) => {
        Seq.unfold(rockStream.next(field.height)) { // Simulate rock movement
          rock => {
            Option.when(rock.exists(_.state == rockMoving)) {
              val updated = rock.blowJet(field).fall(field)
              (updated, updated)
            }
          }
        }
          .last
          .foldLeft(field) { // Update Y bitmasks
            (fieldState, cur) => fieldState + (cur.y -> (fieldState.getOrElse(cur.y, 0) | bits(cur.x)))
          }
      }
    }
  }

  extension (field: Field) {
    private def height: Int = field.size

    private def bitmask(y: Long): Int = field.getOrElse(y, 0)

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

  extension (rock: Rock) {
    private def bitmask: Int = rock.foldLeft(0) { (b, r) => b | bits(r.x) }

    private def blowJet(field: Field): Rock = {
      val jet = jetStream.next
      rock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlockedHorizontal(jet, field)) 0 else 1
          rockElement(r.x + (direction * blocked), r.y, r.state)
        })
    }

    private def fall(field: Field): Rock = {
      rock
        .map(r => {
          rockElement(
            r.x,
            r.y - (if (isBlockedVertical(field)) 0 else 1),
            if (isBlockedVertical(field)) rockStatic else rockMoving
          )
        })
    }

    private def isBlockedHorizontal(jet: Char, field: Field): Boolean = {
      val move = if (jet == '>') 1 else -1
      rock.exists(r =>
        Seq(-1, fieldWidth).contains(r.x + move) ||
          (field.bitmask(r.y) & bits(r.x + move)) == bits(r.x + move)
      )
    }

    private def isBlockedVertical(field: Field): Boolean = {
      rock.exists(r => {
        r.y - 1 == -1 ||
          (field.bitmask(r.y - 1) & bits(r.x)) == bits(r.x)
      })
    }
  }

  private class JetStream {
    private val jets: String = util.get("day17.txt").head

    private val LongList: LazyList[Long] = 0L #:: 1L #:: LongList.tail.map { n => n + 1 }

    private val iterator: Iterator[Long] = LongList.iterator

    def next: Char = jets((iterator.next() % jets.length.toLong).toInt)
  }

  private class RockStream {
    private val LongList: LazyList[Long] = 0L #:: 1L #:: LongList.tail.map { n => n + 1 }

    private val iterator: Iterator[Long] = LongList.iterator

    def next(top: Long): Rock = {
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

      xys.map(xy => rockElement(xy._1, xy._2, rockMoving))
    }
  }

  private case class rockElement(x: Int, y: Long, state: Char)
}
