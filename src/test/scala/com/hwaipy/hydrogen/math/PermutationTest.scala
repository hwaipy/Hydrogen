package com.hwaipy.hydrogen.math

import org.scalatest._
import com.hwaipy.hydrogen.math.Permutation._

class PermutationTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {
  override def beforeAll() {
  }

  override def afterAll() {
  }

  before {
  }

  after {
  }

  test("Test PermutationIterator.") {
    val exp = List(List(2, 3, 4, 5), List(2, 3, 4, 6), List(2, 3, 4, 7), List(2, 3, 4, 8), List(2, 3, 4, 9),
      List(2, 3, 5, 6), List(2, 3, 5, 7), List(2, 3, 5, 8), List(2, 3, 5, 9), List(2, 3, 6, 7), List(2, 3, 6, 8),
      List(2, 3, 6, 9), List(2, 3, 7, 8), List(2, 3, 7, 9), List(2, 3, 8, 9), List(2, 4, 5, 6), List(2, 4, 5, 7),
      List(2, 4, 5, 8), List(2, 4, 5, 9), List(2, 4, 6, 7), List(2, 4, 6, 8), List(2, 4, 6, 9), List(2, 4, 7, 8),
      List(2, 4, 7, 9), List(2, 4, 8, 9), List(2, 5, 6, 7), List(2, 5, 6, 8), List(2, 5, 6, 9), List(2, 5, 7, 8),
      List(2, 5, 7, 9), List(2, 5, 8, 9), List(2, 6, 7, 8), List(2, 6, 7, 9), List(2, 6, 8, 9), List(2, 7, 8, 9),
      List(3, 4, 5, 6), List(3, 4, 5, 7), List(3, 4, 5, 8), List(3, 4, 5, 9), List(3, 4, 6, 7), List(3, 4, 6, 8),
      List(3, 4, 6, 9), List(3, 4, 7, 8), List(3, 4, 7, 9), List(3, 4, 8, 9), List(3, 5, 6, 7), List(3, 5, 6, 8),
      List(3, 5, 6, 9), List(3, 5, 7, 8), List(3, 5, 7, 9), List(3, 5, 8, 9), List(3, 6, 7, 8), List(3, 6, 7, 9),
      List(3, 6, 8, 9), List(3, 7, 8, 9), List(4, 5, 6, 7), List(4, 5, 6, 8), List(4, 5, 6, 9), List(4, 5, 7, 8),
      List(4, 5, 7, 9), List(4, 5, 8, 9), List(4, 6, 7, 8), List(4, 6, 7, 9), List(4, 6, 8, 9), List(4, 7, 8, 9),
      List(5, 6, 7, 8), List(5, 6, 7, 9), List(5, 6, 8, 9), List(5, 7, 8, 9), List(6, 7, 8, 9))
    val act = Permutation.iterator(4, 9, 2)
    assert(act.toList == exp)
    assert(act.toList == exp)
  }
}