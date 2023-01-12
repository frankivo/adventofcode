package com.github.frankivo
package tasks

object day18 {
  def main(args: Array[String]): Unit = {
    val x = input.foldLeft(0) {
      (sum, cur) => {
        sum + cur.adjacent.count(input.contains)
      }
    }
    println(x)
    val y = (input.size * 6 ) - x
    println(y)
  }

  extension (c: coordinate) {
    private def adjacent: Set[coordinate] = {
      Set(
        coordinate(c.x - 1, c.y, c.z),
        coordinate(c.x + 1, c.y, c.z),
        coordinate(c.x, c.y - 1, c.z),
        coordinate(c.x, c.y + 1, c.z),
        coordinate(c.x, c.y, c.z - 1),
        coordinate(c.x, c.y, c.z + 1),
      )
    }
  }

  def input: Set[coordinate] = {
    Set(
      coordinate(1, 1, 1),
      coordinate(2, 1, 1),
    )
  }
}
