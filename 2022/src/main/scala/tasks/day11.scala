package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    println(tests)

    println(play(20, true))
    println(play(10_000, false))
  }

  type monkey = (Int, Seq[Long])
  type monkeys = Map[Int, Seq[Long]]

  private def play(rounds: Int, bored: Boolean): Long = {
    val start = startingItems.map(m => (m._1, (m._2, 0L)))

    val end = (0 until rounds).foldLeft(start) {
      (res, _) => playRound(res, bored)
    }

    end.map(_._2._2).toSeq.sorted.takeRight(2).product
  }

  private def playRound(r: Map[Int, (Seq[Long], Long)], bored: Boolean): Map[Int, (Seq[Long], Long)] = {
    r.keys.toSeq.sorted.foldLeft(r) {
      (state, roundMonkey) => {
        val monk = state.find(_._1 == roundMonkey).get

        val op = operate((monk._1, monk._2._1), bored)
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

  private def operate(m: monkey, bored: Boolean): monkey = {
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
      }).map(w =>
        if (bored) w / 3 else w % tests.map(_._2._1).product
      )
    )
  }

  private def divide(m: monkey): monkeys = {
    m._2.map(i => {
      val mt = tests(m._1)
      val to = if (i % mt._1 == 0) mt._2 else mt._3
      (to, i)
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

  private val tests: Map[Int, (Int, Int, Int)] = Seq(
    (5, 6, 1),
    (2, 2, 6),
    (19, 7, 5),
    (7, 0, 4),
    (17, 0, 1),
    (13, 4, 3),
    (3, 2, 7),
    (11, 3, 5)
  ).zipWithIndex
    .map(t => (t._2, t._1))
    .toMap
}