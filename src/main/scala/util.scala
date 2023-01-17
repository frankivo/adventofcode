package com.github.frankivo

import sttp.client3.{HttpURLConnectionBackend, basicRequest}
import sttp.model.Uri

import java.io.{File, FileOutputStream}
import java.nio.charset.StandardCharsets
import scala.io.Source
import scala.util.Using

object util {
  def get(day: Int): Seq[String] = {
    if (sys.env.getOrElse("DEMO", "0").toInt == 1)
      getDemoFile(day)
    else ???
  }

  private def getDemoFile(day: Int): Seq[String] = Source.fromResource(s"demo/day$day.txt").getLines().toSeq

  private def download(filename: String): Unit = {
    val url = s"https://adventofcode.com/2022/day/19/input"
    println(url)
    val uri = Uri.parse(url)
      .getOrElse(throw new Exception(s"Cannot parse URL: $url"))

    val request = basicRequest
      .cookie("session", sys.env.getOrElse("cookie", throw new Exception("No cookie set")))
      .header("User-Agent", "https://github.com/frankivo/adventofcode2022 frank+github@scriptzone.nl")
      .get(uri)
    val backend = HttpURLConnectionBackend()
    val response = request.send(backend)

    if (response.code.isSuccess) {
      val file = File(s"banaan.txt")
      val body = response.body.getOrElse("")
      Using.resource(new FileOutputStream(file)) { writer =>
        writer.write(body.getBytes(StandardCharsets.UTF_8))
      }
    }
  }

  def clamp(i: Int, lower: Int, upper: Int): Int = lower max i min upper
}
