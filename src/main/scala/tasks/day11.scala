package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    println(play())
  }

  type monkey = (Int, Seq[Int])
  type monkeys = Map[Int, Seq[Int]]

  private def play(): Long = {
    val start = startingItems.map(m => (m._1, (m._2, 0)))

    val end = (0 until 20).foldLeft(start) {
      (res, _) => playRound(res)
    }
    end.map(_._2._2).toSeq.sorted.takeRight(2).product
  }

  private def playRound(r: Map[Int, (Seq[Int], Int)]): Map[Int, (Seq[Int], Int)] = {
    r.keys.foldLeft(r) {
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
      m._2.map(i => {
        m._1 match
          case 0 => i * 19
          case 1 => i + 6
          case 2 => i * i
          case 3 => i + 3
      }).map(_ / 3)
    )
  }

  private def divide(m: monkey): monkeys = {
    m._2.map(i => {
      (m._1 match
        case 0 => if (i % 23 == 0) 2 else 3
        case 1 => if (i % 19 == 0) 2 else 0
        case 2 => if (i % 13 == 0) 1 else 3
        case 3 => if (i % 17 == 0) 0 else 1
        , i)
    })
      .groupBy(_._1)
      .map(g => (g._1, g._2.map(_._2)))
  }

  private val startingItems: monkeys = Seq(
    Seq(79, 98),
    Seq(54, 65, 75, 74),
    Seq(79, 60, 97),
    Seq(74),
  ).zipWithIndex
    .map(si => (si._2, si._1))
    .toMap
}