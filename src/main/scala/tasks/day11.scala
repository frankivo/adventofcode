package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    val end = (0 until 20).foldLeft(startingItems) {
      (res, _) => play(res)
    }

    println(end)
  }

  private def play(r: Seq[monkey]): Seq[monkey] = {
    r.foldLeft(r) {
      (state, m) => {
        val monk = state.find(_.nr == m.nr).get

        val op = operate(monk)
        val div = divide(op)
        r.map(x => {
          monkey(x.nr,
            x.nr match
              case m.nr => Seq()
              case _ => state.find(_.nr == x.nr).map(_.items).getOrElse(Seq()) ++ div.getOrElse(x.nr, Seq())
          )
        })
      }
    }
  }

  private def operate(m: monkey): monkey = {
    monkey(m.nr,
      m.items.map(i => {
        m.nr match
          case 0 => i * 19
          case 1 => i + 6
          case 2 => i * i
          case 3 => i + 3
      }).map(_ / 3)
    )
  }

  private def divide(m: monkey): Map[Int, Seq[Int]] = {
    m.items.map(i => {
      (m.nr match
        case 0 => if (i % 23 == 0) 2 else 3
        case 1 => if (i % 19 == 0) 2 else 0
        case 2 => if (i % 13 == 0) 1 else 3
        case 3 => if (i % 17 == 0) 0 else 1
        , i)
    })
      .groupBy(_._1)
      .map(g => (g._1, g._2.map(_._2)))
  }

  private case class monkey(nr: Int, items: Seq[Int])

  private val startingItems: Seq[monkey] = Seq(
    Seq(79, 98),
    Seq(54, 65, 75, 74),
    Seq(79, 60, 97),
    Seq(74),
  ).zipWithIndex
    .map(si => monkey(si._2, si._1))
}