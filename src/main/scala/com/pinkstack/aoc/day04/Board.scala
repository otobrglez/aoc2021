package com.pinkstack.aoc.day04

import com.pinkstack.aoc.day04.core._

object Board {
  private val List(maxRows, maxColumns) = List(5, 5)

  private val winningSlice: Seq[Boolean] = Seq.fill(maxRows)(true)
  private val wonSlice: Seq[Seq[Seq[Number]]] => Boolean =
    _.map(_.flatMap(_.map(_.check))).contains(winningSlice)

  def hasWon(board: Board): Result =
    Option.when(
      wonSlice(board.mapNumbers { (row, column) =>
        board.numbers.find(n => n.row == row && n.column == column).toList
      }))(WonInRow())
      .orElse(
        Option.when(wonSlice(board.mapNumbers { (row, column) =>
          board.numbers.find(n => n.row == column && n.column == row).toList
        }))(WonInColumn()))
      .getOrElse(Tide())

  def applyPick(picks: Int*)(board: Board): Board =
    board.copy(numbers = board.numbers.map(n => n.copy(check = picks.contains(n.value))))
}

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
