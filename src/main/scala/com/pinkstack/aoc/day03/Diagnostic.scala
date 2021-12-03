package com.pinkstack.aoc.day03

import io.estatico.newtype.macros._
import io.estatico.newtype.ops._

import scala.reflect.ClassTag

package object core {
  type Bit = Int
  @newtype final class BNumber(value: Array[Bit])

  object BNumber {
    implicit val classTag: ClassTag[BNumber] = implicitly[ClassTag[Array[Bit]]].coerce
    def mkEmpty(size: Int): BNumber = BNumber.mkNumber(Array.fill(size)(0))
    def mkNumber(value: Array[Int]): BNumber = value.coerce
    def fromString(string: String): BNumber = mkNumber(string.split("").map(_.toInt))
  }

  object syntax {
    implicit val show: BNumber => String = _.coerce.mkString(",")
    implicit def toInt: BNumber => Int = b => Integer.parseInt(b.coerce.mkString, 2)
  }
}

object Diagnostic {
  import core._
  import core.syntax._

  type Sum = Int

  def analyse(report: Array[BNumber]): Int = {
    val empty = BNumber.mkEmpty(report.head.length).coerce.map(_ => Map[Bit, Sum](0 -> 0, 1 -> 0))
    val rates = report.foldLeft(empty) { (agg, number) =>
      agg.zip(number.coerce.map(bit => Map[Bit, Sum](bit -> 1))).map { case (a, n) =>
        a ++ n.map { case (k, v) => k -> (v + a.getOrElse(k, 0)) }
      }
    }
    Seq[Int](
      BNumber.mkNumber(rates.map(_.maxBy(_._2)).map(_._1)),
      BNumber.mkNumber(rates.map(_.minBy(_._2)).map(_._1))
    ).product
  }
}
