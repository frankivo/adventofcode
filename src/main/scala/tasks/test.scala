package com.github.frankivo
package tasks

object test {

  def main(args: Array[String]): Unit = {
    val input = "37983 blaat.txt"
    val regex = """^([0-9]+\s[a-z\.]+)$""".r

    if (regex.matches(input)) {
      println("yes")
    } else {
      println("no")
    }

  }
}
