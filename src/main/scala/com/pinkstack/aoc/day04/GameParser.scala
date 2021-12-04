package com.pinkstack.aoc.day04

import com.pinkstack.aoc.day04.core.Number

object GameParser {
  private val parseBoard: String => Array[Number] =
    _.split("\\n").zipWithIndex.flatMap {
      case (l, row) => l.split("\\W+").filterNot(_ == "").zipWithIndex.map {
        case (n, column) => Number.apply(row, column, n.toInt)
      }
    }

  val parseGame: String => Game =
    _.split("\\n\\n") match {
      case Array(head, tail@_*) =>
        Game.apply(
          head.split(",").map(_.toInt),
          tail.toArray.map(parseBoard andThen Board.apply))
    }
}
