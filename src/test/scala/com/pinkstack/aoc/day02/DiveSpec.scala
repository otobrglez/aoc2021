package com.pinkstack.aoc.day02

import com.pinkstack.aoc.TestSpec
import scala.io.Source

class DiveSpec extends TestSpec {
  val toDirection: String => Direction = {
    case "down" => Direction.Down
    case "up" => Direction.Up
    case "forward" => Direction.Forward
  }

  val parseLine: String => (Direction, Int) = line =>
    line.split(' ') match {
      case Array(direction, length) => toDirection(direction) -> Integer.parseInt(length)
    }

  def readReportFile(): Array[(Direction, Int)] =
    Source.fromResource("day02-input.txt").getLines()
      .map(parseLine).toArray

  val moves: Array[(Direction, Int)] = Array(
    "forward 5",
    "down 5",
    "forward 8",
    "up 3",
    "down 8",
    "forward 2",
  ).map(parseLine)

  "Line parser" should "parse" in {
    parseLine("down 5") shouldEqual (Direction.Down -> 5)
  }

  it should "parse input file" in {
    readReportFile().length shouldEqual 1000
  }

  it should "work on report" in {
    moves.length shouldEqual 6
  }

  "Dive" should "work" in {
    Dive.positionCode(moves) shouldEqual 150
  }

  it should "compute on my input" in {
    Dive.positionCode(readReportFile()) shouldEqual 2_272_262
  }

  "Deep dive" should "work" in {
    Dive.aimedPosition(moves) shouldEqual Position(15, 60, 10)
    Dive.aimedPositionCode(moves) shouldEqual 900
  }

  it should "compute my input" in {
    Dive.aimedPositionCode(readReportFile()) shouldEqual(2_134_882_034)
  }
}
