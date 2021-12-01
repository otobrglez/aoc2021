package com.pinkstack.aoc.day01

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

import scala.io.Source

abstract class TestSpec extends AnyFlatSpec
  with should.Matchers
  with OptionValues with Inside with Inspectors

class SonarSpec extends TestSpec {
  def readReportFile(): Array[Int] =
    Source.fromResource("day01-input.txt").getLines()
      .map(Integer.parseInt).toArray

  val report = Array(
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
