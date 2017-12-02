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
    val exp1 = List(List(2, 3, 4, 5), List(2, 3, 4, 6), List(2, 3, 4, 7), List(2, 3, 4, 8), List(2, 3, 4, 9),
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
    val act1 = Permutation.iterator(4, 9, 2)
    assert(act1.toList == exp1)

    val exp2 = List(List(1, 1, 1, 1), List(1, 1, 1, 2), List(1, 1, 1, 3), List(1, 1, 1, 4), List(1, 1, 1, 5),
      List(1, 1, 1, 6), List(1, 1, 2, 2), List(1, 1, 2, 3), List(1, 1, 2, 4), List(1, 1, 2, 5), List(1, 1, 2, 6), List(1, 1, 3, 3), List(1, 1, 3, 4), List(1, 1, 3, 5), List(1, 1, 3, 6),
      List(1, 1, 4, 4), List(1, 1, 4, 5), List(1, 1, 4, 6), List(1, 1, 5, 5), List(1, 1, 5, 6), List(1, 1, 6, 6),
      List(1, 2, 2, 2), List(1, 2, 2, 3), List(1, 2, 2, 4), List(1, 2, 2, 5), List(1, 2, 2, 6), List(1, 2, 3, 3), List(1, 2, 3, 4), List(1, 2, 3, 5),
      List(1, 2, 3, 6), List(1, 2, 4, 4), List(1, 2, 4, 5), List(1, 2, 4, 6), List(1, 2, 5, 5), List(1, 2, 5, 6),
      List(1, 2, 6, 6), List(1, 3, 3, 3), List(1, 3, 3, 4), List(1, 3, 3, 5), List(1, 3, 3, 6), List(1, 3, 4, 4), List(1, 3, 4, 5), List(1, 3, 4, 6),
      List(1, 3, 5, 5), List(1, 3, 5, 6), List(1, 3, 6, 6), List(1, 4, 4, 4), List(1, 4, 4, 5), List(1, 4, 4, 6),
      List(1, 4, 5, 5), List(1, 4, 5, 6), List(1, 4, 6, 6), List(1, 5, 5, 5), List(1, 5, 5, 6), List(1, 5, 6, 6), List(1, 6, 6, 6),
      List(2, 2, 2, 2), List(2, 2, 2, 3), List(2, 2, 2, 4), List(2, 2, 2, 5), List(2, 2, 2, 6), List(2, 2, 3, 3),
      List(2, 2, 3, 4), List(2, 2, 3, 5), List(2, 2, 3, 6), List(2, 2, 4, 4), List(2, 2, 4, 5), List(2, 2, 4, 6), List(2, 2, 5, 5),
      List(2, 2, 5, 6), List(2, 2, 6, 6), List(2, 3, 3, 3), List(2, 3, 3, 4), List(2, 3, 3, 5), List(2, 3, 3, 6), List(2, 3, 4, 4), List(2, 3, 4, 5), List(2, 3, 4, 6), List(2, 3, 5, 5), List(2, 3, 5, 6), List(2, 3, 6, 6), List(2, 4, 4, 4),
      List(2, 4, 4, 5), List(2, 4, 4, 6), List(2, 4, 5, 5), List(2, 4, 5, 6), List(2, 4, 6, 6), List(2, 5, 5, 5), List(2, 5, 5, 6), List(2, 5, 6, 6), List(2, 6, 6, 6), List(3, 3, 3, 3), List(3, 3, 3, 4), List(3, 3, 3, 5), List(3, 3, 3, 6),
      List(3, 3, 4, 4), List(3, 3, 4, 5), List(3, 3, 4, 6), List(3, 3, 5, 5), List(3, 3, 5, 6), List(3, 3, 6, 6), List(3, 4, 4, 4), List(3, 4, 4, 5), List(3, 4, 4, 6), List(3, 4, 5, 5), List(3, 4, 5, 6), List(3, 4, 6, 6), List(3, 5, 5, 5),
      List(3, 5, 5, 6), List(3, 5, 6, 6), List(3, 6, 6, 6), List(4, 4, 4, 4), List(4, 4, 4, 5), List(4, 4, 4, 6), List(4, 4, 5, 5), List(4, 4, 5, 6), List(4, 4, 6, 6), List(4, 5, 5, 5), List(4, 5, 5, 6), List(4, 5, 6, 6), List(4, 6, 6, 6),
      List(5, 5, 5, 5), List(5, 5, 5, 6), List(5, 5, 6, 6), List(5, 6, 6, 6), List(6, 6, 6, 6))
    val act2 = Permutation.iterator(4, 6, 1, true)
    assert(act2.toList == exp2)
  }
}