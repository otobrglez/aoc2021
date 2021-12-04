package com.pinkstack.aoc

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.{Inside, Inspectors, OptionValues}

import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.reflect.ClassTag

abstract class TestSpec extends AnyFlatSpec
  with should.Matchers
  with OptionValues with Inside with Inspectors {

  def withInputFile[B](path: String)(f: String => B)
                      (implicit evidence: ClassTag[B]): Array[B] =
    Source.fromResource(path).getLines().map(f).toArray[B]

  def inputFile[F](path: String): String =
    new String(Files.readAllBytes(Paths.get(getClass.getResource("/" + path).getPath)))
}
