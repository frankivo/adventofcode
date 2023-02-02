package com.github.frankivo

import sttp.client3
import sttp.client3.HttpURLConnectionBackend
import sttp.model.Uri

import java.io.{File, FileOutputStream}
import java.nio.charset.StandardCharsets
import scala.io.Source
import scala.util.Using

object util {
  def get(day: Int): Seq[String] = {
    if (sys.env.getOrElse("DEMO", "0").toInt == 1)
      getDemoFile(day)
    else
      getInputFile(day)
  }

  private def getDemoFile(day: Int): Seq[String] =
    Using(Source.fromResource(s"demo/day$day.txt")) { reader => reader.getLines().toSeq }.get

  private def getInputFile(day: Int): Seq[String] = {
    File("input").mkdir()
    val fullPath = File(s"input/$day.txt")

    if (!fullPath.exists())
      download(day, fullPath)

    Using(Source.fromFile(fullPath)) { reader => reader.getLines().toSeq }.get
  }

  private def download(day: Int, path: File): Unit = {
    val url = s"https://adventofcode.com/2022/day/$day/input"
    println(s"Download $url")
    val uri = Uri.parse(url).getOrElse(throw new Exception(s"Cannot parse URL: $url"))

    val request = client3.basicRequest
      .cookie("session", sys.env.getOrElse("cookie", throw new Exception("No cookie set")))
      .header("User-Agent", "https://github.com/frankivo/adventofcode frank+github@scriptzone.nl")
      .get(uri)
    val backend = HttpURLConnectionBackend()
    val response = request.send(backend)

    if (response.code.isSuccess) {
      val body = response.body.getOrElse("")
      Using.resource(new FileOutputStream(path)) { writer =>
        writer.write(body.getBytes(StandardCharsets.UTF_8))
      }
    }
  }

  def clamp(i: Int, lower: Int, upper: Int): Int = lower max i min upper
}
