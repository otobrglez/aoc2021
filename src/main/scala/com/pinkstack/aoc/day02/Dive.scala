package com.pinkstack.aoc.day02

sealed trait Direction

object Direction {
  object Forward extends Direction
  object Down extends Direction
  object Up extends Direction
}

final case class Position(horizontal: Int, depth: Int, aim: Int)

object Position {
  val empty: Position = Position(0, 0, 0)
}

object Dive {
  import Direction._

  // Position calculation for part I.
  val position: Array[(Direction, Int)] => Position =
    _.foldLeft(Position.empty) {
      case (agg, (Forward, length)) => agg.copy(horizontal = agg.horizontal + length)
      case (agg, (Down, length)) => agg.copy(depth = agg.depth + length)
      case (agg, (Up, length)) => agg.copy(depth = agg.depth - length)
    }

  // Position calculation for part II.
  val aimedPosition: Array[(Direction, Int)] => Position =
    _.foldLeft(Position.empty) {
      case (agg, (Forward, length)) =>
        agg.copy(horizontal = agg.horizontal + length, depth = agg.depth + agg.aim * length)
      case (agg, (Down, length)) =>
        agg.copy(aim = agg.aim + length)
      case (agg, (Up, length)) =>
        agg.copy(aim = agg.aim - length)
    }

  val finalComp: Position => Int = p => p.horizontal * p.depth
  val positionCode: Array[(Direction, Int)] => Int = position andThen finalComp
  val aimedPositionCode: Array[(Direction, Int)] => Int = aimedPosition andThen finalComp
}
