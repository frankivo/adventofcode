package com.github.frankivo
package tasks

object day11 {
  def main(args: Array[String]): Unit = {
    val start = Map(0 -> startingItems)
    println(start)
    play(start(0))
  }

  private def play(r: Seq[monkey]): Seq[monkey] = {
    val op = r.map(operate)
    println(op)
    op
  }

  private def operate(m: monkey): monkey = {
      monkey(m.nr,
        m.items.map(i => {
          m.nr match
            case 0 => i * 19
            case 1 => i + 6
            case 2 => 1 * 1
            case 3 => i + 3
        })
      )
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