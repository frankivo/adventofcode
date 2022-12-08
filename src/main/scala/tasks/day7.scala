package com.github.frankivo
package tasks

object day7 {
  def main(args: Array[String]): Unit = {
    println(s"Sum of directories under 100000: ${findSmallDirsTotal()}")
  }

  val tree: Map[String, Long] = {
    util.get("day7.txt")
      .foldLeft(("", Map[String, Long]())) {
        (browser, current) => {
          val path = newPath(browser._1, current)
          val files = {
            if (current.exists(_.isDigit)) {
              val data = current.split(" ")
              val file = (path + data.last, data.head.toLong)
              browser._2 + file
            }
            else
              browser._2
          }
          (path, files)
        }
      }
      ._2
  }

  def findSmallDirsTotal(maxSize: Long = 100_000): Long = {
    dirSizes
      .filter(_._2 <= maxSize)
      .values
      .sum
  }

  def dirSizes: Map[String, Long] = {
    val dirs = tree
      .keys
      .map(getDir)
      .flatMap(d => {
        (1 to d.count(_.equals('/')))
          .map(idx => {
            d.split("/").take(idx).mkString("/") + "/"
          })
      })
    dirs.map(dir => (dir, tree.filter(_._1.startsWith(dir)).values.sum)).toMap
  }

  def getDir(path: String): String = path.split("/").dropRight(1).mkString("/") + "/"

  def newPath(old: String, command: String): String = {
    if (!command.startsWith("$ cd")) return old

    val action = command.split(" ").last
    action match
      case "/" => action
      case ".." => old.split("/").dropRight(1).mkString("/") + "/"
      case _ => old + action + "/"
  }
}
