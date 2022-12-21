package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    println(play())
  }

  type monkey = (Int, Seq[Long])
  type monkeys = Map[Int, Seq[Long]]

  private def play(): Long = {
    val start = startingItems.map(m => (m._1, (m._2, 0)))

    val end = (0 until 20).foldLeft(start) {
      (res, _) => playRound(res)
    }
    end.map(_._2._2).toSeq.sorted.takeRight(2).product
  }

  private def playRound(r: Map[Int, (Seq[Long], Int)]): Map[Int, (Seq[Long], Int)] = {
    r.keys.toSeq.sorted.foldLeft(r) {
      (state, roundMonkey) => {
        val monk = state.find(_._1 == roundMonkey).get

        val op = operate((monk._1, monk._2._1))
        val div = divide(op)
        state.keys.map(x => {
          (x,
            if (x == roundMonkey)
              Seq()
            else {
              val olditems = state.get(x).map(_._1).getOrElse(Seq())
              val newitems = div.getOrElse(x, Seq())
              olditems ++ newitems
            }
          )
        })
          .map(b => {
            val update = if (b._1 == roundMonkey) monk._2._1.length + state(b._1)._2 else state(b._1)._2
            (b._1, (b._2, update))
          }).toMap
      }
    }
  }

  private def operate(m: monkey): monkey = {
    (m._1,
      m._2.map(old => {
        m._1 match
          case 0 => old * 2
          case 1 => old * 13
          case 2 => old + 5
          case 3 => old + 6
          case 4 => old + 1
          case 5 => old + 4
          case 6 => old + 2
          case 7 => old * old
      }).map(_ / 3)
    )
  }

  private def divide(m: monkey): monkeys = {
    m._2.map(i => {
      (m._1 match
        case 0 => if (i % 5 == 0) 6 else 1
        case 1 => if (i % 2 == 0) 2 else 6
        case 2 => if (i % 19 == 0) 7 else 5
        case 3 => if (i % 7 == 0) 0 else 4
        case 4 => if (i % 17 == 0) 0 else 1
        case 5 => if (i % 13 == 0) 4 else 3
        case 6 => if (i % 3 == 0) 2 else 7
        case 7 => if (i % 11 == 0) 3 else 5
        , i)
    })
      .groupBy(_._1)
      .map(g => (g._1, g._2.map(_._2)))
  }

  private val startingItems: monkeys = Seq(
    Seq(98, 89, 52),
    Seq(57, 95, 80, 92, 57, 78),
    Seq(82, 74, 97, 75, 51, 92, 83),
    Seq(97, 88, 51, 68, 76),
    Seq(63),
    Seq(94, 91, 51, 63),
    Seq(61, 54, 94, 71, 74, 68, 98, 83),
    Seq(90, 56),
  ).zipWithIndex
    .map(si => (si._2, si._1.map(_.toLong)))
    .toMap
}