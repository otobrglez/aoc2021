package com.pinkstack.aoc.day01

object Sonar {
  val increases: Array[Int] => Int = report =>
    report.zipWithIndex.foldLeft(0) { case (agg, (n, i)) =>
      agg + report.lift(i - 1).map(p => Option.when(p < n)(1).getOrElse(0)).getOrElse(0)
    }

  val deepIncreases: Array[Int] => Int = { report =>
    val windows: Array[Int] => Array[Int] = report =>
      report.zipWithIndex
        .map(t => List(-1, 0, 1).map(d => report.lift(t._2 + d)))
        .filterNot(_.exists(_.isEmpty))
        .map(_.foldLeft(0) {
          case (agg, Some(v)) => agg + v
          case (agg, _) => agg
        })

    (windows andThen increases) (report)
  }
}
