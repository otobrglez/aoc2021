package com.pinkstack.aoc.day04

import com.pinkstack.aoc.day04.core._
import com.pinkstack.aoc.TestSpec
import pprint.pprintln

class SquidSpec extends TestSpec {
  val exampleInput =
    """7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
      |
      |22 13 17 11  0
      | 8  2 23  4 24
      |21  9 14 16  7
      | 6 10  3 18  5
      | 1 12 20 15 19
      |
      | 3 15  0  2 22
      | 9 18 13 17  5
      |19  8  7 25 23
      |20 11 10 24  4
      |14 21 16 12  6
      |
      |14 21 17 24  4
      |10 16 15  9 19
      |18  8 23 26 20
      |22 11 13  6  5
      | 2  0 12  3  7""".stripMargin

  "GameParser" should "parse given example" in {
    val game = GameParser.parseGame(exampleInput)
    game.numbers.length shouldEqual 27
    game.boards.length shouldEqual 3
  }

  it should "parse input file" in {
    val game = GameParser.parseGame(inputFile("day04-input.txt"))
    game.numbers.length shouldEqual 100
    game.boards.length shouldEqual 100
  }

  "Example Game" should "have winner" in {
    val game = GameParser.parseGame(exampleInput)
    Board.applyPick(13)(game.boards(0)).numbers.find(_.value == 13).map(_.check) shouldEqual Some(Checked)

    val exampleBoard = game.boards(2)
    Board.hasWon(Board.applyPick()(exampleBoard)) shouldEqual Tide()
    Board.hasWon(Board.applyPick(14, 21, 17, 24, 4, 9, 23, 11, 2, 0, 7, 5)(exampleBoard)) shouldEqual WonInRow()

    // "Diagonals don't count."
    // Board.hasWon(Board.applyPick(21, 17, 24, 4, 9, 23, 11, 2, 0, 7, 5)(exampleBoard)) shouldEqual WonRightDiagonal()
    // Board.hasWon(Board.applyPick(14, 16, 23, 6, 7)(exampleBoard)) shouldEqual WonLeftDiagonal()
  }

  it should "be played" in {
    val game = GameParser.parseGame(exampleInput)
    val winner = Game.play(game)
    winner shouldEqual Some(4512)
  }

  "Real input" should "compute" in {
    val game = GameParser.parseGame(inputFile("day04-input.txt"))
    val winner = Game.play(game)
    winner shouldEqual Some(60_368)
    println(winner)
  }
}
