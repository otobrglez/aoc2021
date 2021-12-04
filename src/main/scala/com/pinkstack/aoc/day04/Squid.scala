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

final case class Board(numbers: Array[Number]) {

  import Board.{maxColumns, maxRows}

  private val printNumber: Int => String = number =>
    String.format("%1$" + 2 + "s", number.toString).replace(' ', ' ')

  private val renderNumber: Number => String = number =>
    if (number.check) s"[%s]".format(printNumber(number.value)) else
      s" %s ".format(String.format("%1$" + 2 + "s",
        printNumber(number.value)).replace(' ', ' '))

  def mapNumbers[B](f: (Row, Column) => B): Seq[Seq[B]] =
    Range.apply(0, maxRows).map(row =>
      Range.apply(0, maxColumns).map(column => f(row, column)))

  override def toString: String =
    mapNumbers { (row, column) =>
      numbers.find(n => n.row == row && n.column == column).map(renderNumber).mkString(" ")
    }.map(_.mkString("")).mkString("\n")
}

object Board {
  private val List(maxRows, maxColumns) = List(5, 5)

  def empty(value: Value = 0): Board =
    Board.apply(
      Range.apply(0, maxRows).toArray.flatMap(r => Range.apply(0, maxColumns)
        .toList.map(c => Number.apply(r, c, value)))
    )

  def applyPick(picks: Int*)(board: Board): Board =
    board.copy(numbers = board.numbers.map(n => n.copy(check = picks.contains(n.value))))

  private val wonSlice: Seq[Seq[Seq[Number]]] => Boolean =
    _.map(_.flatMap(_.map(_.check))).contains(Seq.fill(maxRows)(true))

  def hasWon(board: Board): Result = {
    val rows = wonSlice(board.mapNumbers { (row, column) =>
      board.numbers.find(n => n.row == row && n.column == column).toList
    })

    val columns = wonSlice(board.mapNumbers { (row, column) =>
      board.numbers.find(n => n.row == column && n.column == row).toList
    })

    val leftDiagonal = wonSlice(Seq(Range.apply(0, maxRows).zip(Range.apply(0, maxColumns))
      .map { case (row, columns) => board.numbers.find(n => n.row == row && n.column == columns).toList }))

    val rightDiagonal = wonSlice(Seq(Range.apply(0, maxRows).zip(Range.apply(0, maxColumns).reverse)
      .map { case (row, columns) => board.numbers.find(n => n.row == row && n.column == columns).toList }))

    Option.when(rows)(WonInRow())
      .orElse(Option.when(columns)(WonInColumn()))
      // .orElse(Option.when(leftDiagonal)(WonLeftDiagonal()))
      // .orElse(Option.when(rightDiagonal)(WonRightDiagonal()))
      .getOrElse(Tide())
  }
}

final case class Game(numbers: Array[Int], boards: Array[Board])

object Game {
  val play: Game => Option[Int] = {
    val runGame: Game => Option[(Board, Result, Int)] = game =>
      game.numbers.zipWithIndex
        .map { case (number, i) => (number, game.numbers.slice(0, i + 1)) }
        .flatMap { case (number, numbers) =>
          game.boards.map(board => {
            val newBoard = Board.applyPick(numbers.toIndexedSeq: _*)(board)
            (newBoard, Board.hasWon(newBoard), number)
          })
        }
        .find(_._2 != Tide())

    val computeResult: Option[(Board, Result, Int)] => Option[Int] = {
      case Some((board: Board, _: Won, number)) =>
        Some(board.numbers.filterNot(_.check).map(_.value).sum * number)
      case _ =>
        None
    }

    runGame andThen computeResult
  }
}


