package com.hwaipy.hydrogen.math

import org.scalatest._
import com.hwaipy.hydrogen.math.JScienceUtil._
import org.jscience.mathematics.number.Complex
import org.jscience.mathematics.vector.{ComplexMatrix, ComplexVector}

class JScienceUtilTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {
  private val testVector: ComplexVector = 1 + 16 * i :: 4 - 5 * i :: -2 :: Nil
  private val testMatrix: ComplexMatrix = testVector :: (2 + 1 * i :: 14 - 5 * i :: -2 + I :: Nil) :: (1 :: -i :: -2 * i :: Nil) :: Nil

  override def beforeAll() {
  }

  override def afterAll() {
  }

  before {
  }

  after {
  }

  test("Test Complex calculation.") {
    assert(I == Complex.valueOf(0, 1))
    assert(I + 1 == Complex.valueOf(1, 1))
    assert(((1.2) plus (Complex.I)) == Complex.valueOf(1.2, 1))
    assert(1 + I == Complex.valueOf(1, 1))
    assert(+I == Complex.valueOf(0, 1))
    assert(-I == Complex.valueOf(0, -1))
    assert(I + I == Complex.valueOf(0, 2))
    assert(1 - I == Complex.valueOf(1, -1))
    assert(I * I == Complex.valueOf(-1, 0))
    assert(I / 3 == Complex.valueOf(0, 1.0 / 3))
    assert(3 / I == Complex.valueOf(0, -3))
    assert((4 + 3 * I) ** 3 == (Complex.valueOf(4, 3).pow(3.0)))
    assert((4 + 3 * I) ** (3 + 2 * I) == (Complex.valueOf(4, 3).pow(Complex.valueOf(3.0, 2))))
    assert(4 ** 3 == 64)
    assert(~I == Complex.valueOf(0, -1))
    assert((1 + 0 * I).phase == 0)
    assert((1 + 1 * I).phase == math.Pi / 4)
    assert((1 * I).phase == math.Pi / 2)
    assert((-1 + 1 * I).phase == math.Pi / 4 * 3)
    assert((-1 + 0 * I).phase == math.Pi)
    assert((-1 - 1 * I).phase == math.Pi / 4 * 5)
    assert((-1 * I).phase == math.Pi / 4 * 6)
    assert((1 - 1 * I).phase == math.Pi / 4 * 7)
  }

  test("Test ComplexVector calculation.") {
    assert(testVector.toList == (1 + 16 * i :: 4 - 5 * i :: -2 + 0 * i :: Nil))
    assert((testVector.normalized.toList.map(c => c.square).sum - 1).abs < 0.000001)
    assert(testVector.sum == 3 + 11 * I)
    assert(testVector(2) == -2 + 0 * I)
    assert(+testVector == testVector)
    assert(-testVector == ComplexVector.valueOf(0, 0, 0).minus(testVector))
    assert(~testVector.sum == 3 - 11 * I)
    assert((testVector + testVector).sum == 6 + 22 * I)
    assert((testVector - ~testVector).sum == 22 * I)
    assert((testVector * I).sum == -11 + 3 * I)
    assert((I * testVector).sum == -11 + 3 * I)
    assert((2 * testVector).sum == 6 + 22 * I)
    assert((testVector / 2).sum == 1.5 + 5.5 * I)
    assert((testVector * testVector) == (1 - 16 ** 2 + 32 * i + 16 - 25 - 40 * i + 4))
  }

  test("Test ComplexMatrix calculation.") {
    val exp11: ComplexVector = 1 + 16 * i :: 4 - 5 * i :: -2 :: Nil
    val exp12: ComplexVector = 2 + 1 * i :: 14 - 5 * i :: -2 + I :: Nil
    val exp13: ComplexVector = 1 :: -i :: -2 * i :: Nil
    assert(testMatrix.getRowVectorList == exp11 :: exp12 :: exp13 :: Nil)
    val exp21: ComplexVector = (1 + 16 * i :: 2 + 1 * i :: 1 :: Nil)
    val exp22: ComplexVector = (4 - 5 * i :: 14 - 5 * i :: -i :: Nil)
    val exp23: ComplexVector = (-2 :: -2 + I :: -2 * i :: Nil)
    assert(testMatrix.getColumnVectorList == exp21 :: exp22 :: exp23 :: Nil)
    val exp3: ComplexVector = (1 + 16 * i :: 4 - 5 * i :: -2 :: 2 + 1 * i :: 14 - 5 * i :: -2 + I :: 1 :: -i :: -2 * i :: Nil)
    assert(testMatrix.flatten == exp3.toList)
  }
}
