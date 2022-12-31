package com.github.frankivo
package tasks

object day15 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    input.foreach(i => {
      println(s"$i : ${i.distance}")
    })
  }

  private case class sensor(sensor: coordinate, beacon: coordinate) {
    def distance: Int = (sensor.x - beacon.x).abs + (sensor.y - beacon.y).abs
  }

  private val input: Set[sensor] = {
    util.get("day15.txt")
      .flatMap("""([\-0-9]+)""".r.findAllMatchIn)
      .map(_.matched.toInt)
      .grouped(4)
      .map(g => {
        sensor(
          coordinate(g(0), g(1), "S"),
          coordinate(g(2), g(3), "B")
        )
      })
      .toSet
  }
}
