package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    play()
  }

  type monkey = (Int, Seq[Int])
  type monkeys = Map[Int, Seq[Int]]

  private def play(): Unit = {
    val start = startingItems.map(m => (m._1, (m._2, 0)))

    val end = (0 until 20).foldLeft(start) {
      (res, _) => playRound(res)
    }

    println(end)
  }

  private def playRound(r: Map[Int, (Seq[Int], Int)]): Map[Int, (Seq[Int], Int)] = {
    val ret = r.keys.foldLeft(r) {
      (state, m) => {
        val mstate = state.find(_._1 == m).get
        val monk = (mstate._1, mstate._2._1)

        val op = operate(monk)
        val div = divide(op)
        r.map(x => {
          val mNr = x._1
          val items = {
            if (x._1 == m)
              Seq()
            else {
              val olditems = state.get(x._1).map(_._1).getOrElse(Seq())
              val newitems = div.getOrElse(x._1, Seq())
              olditems ++ newitems
            }
          }
          val itemCount = x._2._1.length
          (mNr, (items, mstate._2._2 + itemCount))
        })
      }
    }
    ret
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