package com.pinkstack.aoc.day04

package object core {
  type Row = Int
  type Column = Int
  type Value = Int
  type Check = Boolean
  val Checked: Check = true
  val Unchecked: Check = false

  sealed trait Result extends Product with Serializable

  sealed trait Won extends Result with Product with Serializable

  final case class WonInRow() extends Won

  final case class WonInColumn() extends Won

  final case class WonLeftDiagonal() extends Won

  final case class WonRightDiagonal() extends Won

  final case class Tide() extends Result

  final case class Number(row: Row, column: Column,
                          value: Value, check: Check = Unchecked)
}

import com.pinkstack.aoc.day04.core._

final case class Game(numbers: Array[Int], boards: Array[Board])

object Game {
  type CurrentNumber = Int
  type Index = Int

  private val withNumbers: Game => Array[(Int, Array[Int], Int)] = game =>
    game.numbers.zipWithIndex
      .map { case (number, i) => (number, game.numbers.slice(0, i + 1), i) }

  private val runGame: Game => Array[(Board, Result, CurrentNumber, Index)] = game =>
    for {
      number <- withNumbers(game)
      board <- game.boards.map { board =>
        val newBoard = Board.applyPick(number._2.toIndexedSeq: _*)(board)
        (newBoard, Board.hasWon(newBoard), number._1, number._3)
      }
    } yield board

  private val runBoards: Game => Array[Array[(Board, Result, CurrentNumber, Index)]] = game =>
    for {
      board <- game.boards
      winBoards = withNumbers(game).map { number =>
        val newBoard = Board.applyPick(number._2.toIndexedSeq: _*)(board)
        (newBoard, Board.hasWon(newBoard), number._1, number._3)
      }
    } yield winBoards


  private val computeResult: Option[(Board, Result, CurrentNumber, Index)] => Option[Int] = {
    case Some((board: Board, _: Won, number, _)) =>
      Some(board.numbers.filterNot(_.check).map(_.value).sum * number)
    case _ =>
      None
  }

  // Computation for part I.
  val play: Game => Option[Int] = {
    val findGame = (game: Game) => runGame(game).find(_._2 != Tide())
    findGame andThen computeResult
  }

  // Computation for part II.
  val playLast: Game => Option[Int] = {
    val findWorse: Array[Array[(Board, Result, CurrentNumber, Index)]] =>
      Option[(Board, Result, CurrentNumber, Index)] =
      _.map {
        _.collectFirst {
          case (board, result: Won, number, i) => (board, result, number, i)
        }.minBy(_._4)
      }.maxByOption(_._4)

    runBoards andThen findWorse andThen computeResult
  }
}
