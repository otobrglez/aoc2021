package com.pinkstack.aoc.day01

import com.pinkstack.aoc.TestSpec
import scala.io.Source

class SonarSpec extends TestSpec {
  def readReportFile(): Array[Int] =
    Source.fromResource("day01-input.txt").getLines()
      .map(Integer.parseInt).toArray

  val report: Array[Int] = Array(
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
    Sonar.increases(report) shouldEqual 7
  }

  it should "compute result" in {
    Sonar.increases(readReportFile()) shouldEqual 1292
  }

  "Deeper" should "work" in {
    Sonar.deepIncreases(report) shouldEqual 5
  }

  it should "compute result for second part" in {
    Sonar.deepIncreases(readReportFile()) shouldEqual 1262
  }
}
