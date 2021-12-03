ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "aoc2021",

    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "pprint" % "0.5.6",
      "io.estatico" % "newtype_2.13" % "0.4.4",
      "org.scalactic" %% "scalactic" % "3.2.10",
      "org.scalatest" %% "scalatest" % "3.2.10" % "test",
      "org.scalatest" %% "scalatest-flatspec" % "3.2.10" % "test"
    ),

    resolvers ++= Seq(
      "Artima Maven Repository" at "https://repo.artima.com/releases"
    ),

    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
      "-language:existentials",
      "-language:dynamics",
      "-language:higherKinds",
      "-language:postfixOps",
      "-Ymacro-annotations"
    ),
  )
