ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "adventofcode2022",
    idePackagePrefix := Some("com.github.frankivo"),

    libraryDependencies ++= Seq(
        "com.google.code.gson" % "gson" % "2.10",
    )
)
