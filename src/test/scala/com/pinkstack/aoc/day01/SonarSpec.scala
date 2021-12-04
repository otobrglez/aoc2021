package com.pinkstack.aoc.day01

import com.pinkstack.aoc.TestSpec
import scala.io.Source

class SonarSpec extends TestSpec {
  val inputReport: Array[Int] = withInputFile("day01-input.txt")(Integer.parseInt)

  val exampleReport: Array[Int] = Array(
    199,
    200,
    208,
    210,
    200,
    207,
    240,
    269,
    260,
    263,
  )

  "Sonar" should "work" in {
    Sonar.increases(exampleReport) shouldEqual 7
  }

  it should "compute result" in {
    Sonar.increases(inputReport) shouldEqual 1292
  }

  "Deeper" should "work" in {
    Sonar.deepIncreases(exampleReport) shouldEqual 5
  }

  it should "compute result for second part" in {
    Sonar.deepIncreases(inputReport) shouldEqual 1262
  }
}
