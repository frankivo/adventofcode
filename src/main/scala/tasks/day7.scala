package com.github.frankivo
package tasks

object day7 {
  def main(args: Array[String]): Unit = {
    println(tree)
  }

  val tree: Map[String, Long] = {
    util.get("day7.txt")
      .foldLeft(("", Map[String, Long]())) {
        (browser, current) => {
          val path = {
            if (current.startsWith("$ cd"))
              newPath(browser._1, current)
            else browser._1
          }
          val files = {
            if (current.exists(_.isDigit)) {
              val data  = current.split(" ").toList
              val file = (path + data.last, data.head.toLong)
              browser._2 + file
            }
            else browser._2
          }
          (path, files)
        }
      }
      ._2
  }

  def newPath(old: String, command: String): String = {
    val action = command.split(" ").last
    action match
      case "/" => action
      case ".." => old.split("/").dropRight(1).mkString("/") + "/"
      case _ => old + action + "/"
  }
}
