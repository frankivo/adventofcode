package com.github.frankivo
package tasks

object day14 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    println(input)
  }

  val input: Seq[coordinate] = {
    util.get("day14.txt")
      .map(l => {
        l.split("->").map(_.trim).sliding(2).toSeq
          .map(p => {
            val c = p.map(_.split(",").toSeq).sorted

            

            println(s"${c.head} to ${c.last}")

          })
      })
      .foreach(println)

    Seq()
  }
}
