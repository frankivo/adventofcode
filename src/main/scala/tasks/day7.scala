package com.github.frankivo
package tasks

object day7 {
  def main(args: Array[String]): Unit = {
    println(dirSizes)
    println(findSmallDirsTotal)
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
              val data = current.split(" ").toList
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

  def findSmallDirsTotal: Long = {
    dirSizes
      .filter(_._2 <= 10000)
      .values
      .sum
  }

  def dirSizes: Map[String, Long] = {
    tree
      .toSeq
      .map(f => (getDir(f._1), f._2))
      .groupBy(_._1)
      .map(d => (d._1, d._2.map(_._2).sum))
  }

  def getDir(path: String): String = path.split("/").dropRight(1).mkString("/") + "/"

  def newPath(old: String, command: String): String = {
    val action = command.split(" ").last
    action match
      case "/" => action
      case ".." => old.split("/").dropRight(1).mkString("/") + "/"
      case _ => old + action + "/"
  }
}
