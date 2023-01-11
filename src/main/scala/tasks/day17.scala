package com.github.frankivo
package tasks

import scala.collection.mutable
import scala.util.Try

object day17 {
  def main(args: Array[String]): Unit = {
    println(s"Tower is ${part1()} units tall")
    println(s"Tower is ${part2()} units tall")
  }

  private val fieldWidth = 7
  private val rockStatic = "#"
  private val rockMoving = "@"
  private val air = "."
  private val bits = (0 until 7).map(i => (i, 1 << i)).toMap

  private type Field = Map[Int, Int] // Y, bitmask
  private type Rock = Seq[coordinate]

  private def part1(): Long = solve(2022)

  private def part2(): Long = solve(1_000_000_000_000L)

  private def solve(moves: Long): Long = {
    val jetStream = JetStream()
    val rockStream = RockStream()

    val cache = mutable.Map.empty[(Long, Set[Int]), (Long, Long)]
    var added = 0L

    Seq.unfold(Map.empty[Int, Int], 0L) {
      (field, t) => {
        Option.when(t < moves) {
          val updatedField = Seq.unfold(rockStream.next(field.height)) { // Simulate rock movement
            rock => {
              Option.when(rock.exists(_.name == rockMoving)) {
                val updated = rock.blowJet(jetStream.next, field).fall(field)
                (updated, updated)
              }
            }
          }
            .last
            .foldLeft(field) { // Update Y bitmasks
              (fieldState, cur) => fieldState + (cur.y -> (fieldState.getOrElse(cur.y, 0) | bits(cur.x)))
            }

          val sig = (t % jetStream.size, field.signature)
          val top = field.height

          val increase = if (cache.contains(sig) && added == 0 && t > 2022) {
            val (oldt, oldy) = cache(sig)
            val dy = top - oldy
            val dt = t - oldt
            val amt = ((moves - t) / dt.toDouble).floor.toLong
            added += amt * dy -1
            amt * dt
          } else 1

          cache += sig -> (t, field.height)

          (updatedField.size, (updatedField, t + increase))
        }
      }
    }.last + added
  }

  extension (field: Field) {
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

    private def signature: Set[Int] = {
      //      val topRows = field.filter(_._1 > height - 30)
      //      topRows.map(y => {
      //        (0 until fieldWidth)
      //          .flatMap(x => {
      //            Option.when((y._2 & bits(x)) == bits(x)) {
      //              (x, height - y._1)
      //            }
      //          })
      //      })
      //        .flatten
      //        .toSet

      field.filter(_._1 > height - 30).values.toSet
    }
  }

  extension (rock: Rock) {
    private def blowJet(jet: Char, field: Field): Rock = {
      rock
        .map(r => {
          val direction = if (jet == '>') 1 else -1
          val blocked = if (isBlockedHorizontal(jet, field)) 0 else 1
          coordinate(r.x + (direction * blocked), r.y, r.name)
        })
    }

    private def fall(field: Field): Rock = {
      rock
        .map(r => {
          coordinate(
            r.x,
            r.y - (if (isBlockedVertical(field)) 0 else 1),
            if (isBlockedVertical(field)) rockStatic else rockMoving
          )
        })
    }

    private def isBlockedHorizontal(jet: Char, field: Field): Boolean = {
      val move = if (jet == '>') 1 else -1
      rock.exists(r =>
        Seq(-1, fieldWidth).contains(r.x + move) || (field.bitmask(r.y) & bits(r.x + move)) == bits(r.x + move)
      )
    }

    private def isBlockedVertical(field: Field): Boolean =
      rock.exists(r => r.y - 1 == -1 || (field.bitmask(r.y - 1) & bits(r.x)) == bits(r.x))
  }

  private class JetStream {
    private val jets: String = util.get("day17.txt").head

    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next: Char = jets(iterator.next() % jets.length)

    def size: Int = jets.length
  }

  private class RockStream {
    private val iterator: Iterator[Int] = LazyList.from(0).iterator

    def next(top: Int): Rock = {
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
