package com.pinkstack.aoc

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.{Inside, Inspectors, OptionValues}

abstract class TestSpec extends AnyFlatSpec
  with should.Matchers
  with OptionValues with Inside with Inspectors
