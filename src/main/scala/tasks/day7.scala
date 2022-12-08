package com.github.frankivo
package tasks

object day7 {
  def main(args: Array[String]): Unit = {
    println(findSmallDirsTotal())
  }

  val tree: Map[String, Long] = {
    util.get("day7.txt")
      .foldLeft(("", Map[String, Long]())) {
        (browser, current) => {
          val path = newPath(browser._1, current)
          val files = {
            val file = {
              if (current.exists(_.isDigit)) {
                val data = current.split(" ").last
                (path + data.last, data.head.toLong)
              }
              else
                (path + "dummy.frankivo", 0L) // Dirty hack, to let empty dirs appear in the tree.
            }
            browser._2 + file
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
    tree.keys.map(getDir) // Directories
      .map(dir => (dir, tree.filter(_._1.startsWith(dir)).values.sum))
      .toMap
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
