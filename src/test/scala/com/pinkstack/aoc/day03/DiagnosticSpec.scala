package com.pinkstack.aoc.day03

import pprint.pprintln
import com.pinkstack.aoc.TestSpec
import core._, core.syntax._

class DiagnosticSpec extends TestSpec {
  val example: Array[BNumber] =
    """00100
      |11110
      |10110
      |10111
      |10101
      |01111
      |00111
      |11100
      |10000
      |11001
      |00010
      |01010
      |""".stripMargin.split('\n').map(BNumber.fromString)

  "Diagnostic" should "work for given example" in {
    Diagnostic.analyse(example) shouldEqual 198
  }

  it should "have conversion" in {
    BNumber.fromString("0").toInt shouldEqual 0
    BNumber.fromString("1").toInt shouldEqual 1
    BNumber.fromString("10110").toInt shouldEqual 22
    BNumber.fromString("10110") shouldEqual Array[Bit](1, 0, 1, 1, 0)
  }

  "Part I" should "work for input" in {
    val report: Array[BNumber] = withInputFile("day03-input.txt")(BNumber.fromString)
    Diagnostic.analyse(report) shouldEqual 1_131_506
  }

  "Part II" should "work on given example" in {
    println(example.length)
    println(Diagnostic.lifeSupportRating(example))
  }
}
