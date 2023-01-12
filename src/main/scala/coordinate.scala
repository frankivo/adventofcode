package com.github.frankivo

case class coordinate(x: Int, y: Int, z: Int, name: String) {
  def distance(other: coordinate): Int = Seq((x - other.x).abs, (y - other.y).abs).max
}

object coordinate {
  def apply(x: Int, y: Int, z: Int): coordinate = new coordinate(x, y, z, "?")

  def apply(x: Int, y: Int, name: String = "?"): coordinate = new coordinate(x, y, 0, name)
}
