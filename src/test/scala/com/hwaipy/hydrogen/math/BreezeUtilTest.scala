package com.hwaipy.hydrogen.math

import org.scalatest._
import breeze.math.Complex
import BreezeUtil._
import breeze.linalg._


class BreezeUtilTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {
  private val testVector: DenseVector[Complex] = 1 + 16 * i :: 4 - 5 * i :: -2 :: Nil
  private val testMatrix: DenseMatrix[Complex] = testVector :: (2 + 1 * i :: 14 - 5 * i :: -2 + i :: Nil) :: (1 :: -i :: -2 * i :: Nil) :: Nil

  override def beforeAll() {
  }

  override def afterAll() {
  }

  before {
  }

  after {
  }

  test("Test Complex calculation.") {
    assert(i == Complex(0, 1))
    assert(i + 1 == Complex(1, 1))
    assert(((1.2) + (i)) == Complex(1.2, 1))
    assert(1 + i == Complex(1, 1))
    assert(+i == Complex(0, 1))
    assert(-i == Complex(0, -1))
    assert(i + i == Complex(0, 2))
    assert(1 - i == Complex(1, -1))
    assert(i * i == Complex(-1, 0))
    assert(i / 3 == Complex(0, 1.0 / 3))
    assert(3 / i == Complex(0, -3))
    assert((4 + 3 * i) ** 2 =~ Complex(7, 24.0000))
    assert(!((4 + 3 * i) ** 2 =~ Complex(7, 24.00001)))
    assert((4 + 3 * i) ** 2 !=~ Complex(7, 24.00001))
    assert((4 + 3 * i) ** 3 == (Complex(4, 3).pow(3.0)))
    assert((4 + 3 * i) ** (3 + 2 * i) == (Complex(4, 3).pow(Complex(3.0, 2))))
    assert(4 ** 3 == 64)
    assert(~i == Complex(0, -1))
    assert((1 + 0 * i).phase == 0)
    assert((1 + 1 * i).phase == math.Pi / 4)
    assert((1 * i).phase == math.Pi / 2)
    assert((-1 + 1 * i).phase == math.Pi / 4 * 3)
    assert((-1 + 0 * i).phase == math.Pi)
    assert((-1 - 1 * i).phase == -math.Pi / 4 * 3)
    assert((-1 * i).phase == -math.Pi / 4 * 2)
    assert((1 - 1 * i).phase == -math.Pi / 4 * 1)
  }

  test("Test ComplexVector calculation.") {
    assert(testVector.toList == (1 + 16 * i :: 4 - 5 * i :: -2 + 0 * i :: Nil))
    assert(math.abs(testVector.normalized.toList.map(c => c.square).sum - 1) < 0.000001)
    assert(sum(testVector) == 3 + 11 * i)
    assert(testVector(2) == -2 + 0 * i)
    assert(+testVector == testVector)
    assert(-testVector == Vector(0 + 0 * i, 0 + 0 * i, 0 + 0 * i) - testVector)
    assert(sum(~testVector) == 3 - 11 * i)
    assert(sum(testVector + testVector) == 6 + 22 * i)
    assert(sum(testVector - ~testVector) == 22 * i)
    assert(sum(testVector * i) == -11 + 3 * i)
    assert(sum(i * testVector) == -11 + 3 * i)
    assert(sum(2 * testVector) == 6 + 22 * i)
    assert(sum(testVector * 2.0) == (6 + 22 * i))
    assert(sum(testVector / i) == 11 - 3 * i)
    assert(sum(testVector / 2) == 1.5 + 5.5 * i)
    assert(sum(testVector / 2.0) == 1.5 + 5.5 * i)
    assert((testVector dot testVector) == (1 - 16 ** 2 + 32 * i + 16 - 25 - 40 * i + 4))
    assert(testVector.square == (1 + 16 ** 2 + 16 + 25 + 4))
    assert(sum(testVector(1 to 2)) == 2 - 5 * i)
  }

  test("Test ComplexMatrix calculation.") {
    val exp11: DenseVector[Complex] = 1 + 16 * i :: 4 - 5 * i :: -2 :: Nil
    val exp12: DenseVector[Complex] = 2 + 1 * i :: 14 - 5 * i :: -2 + i :: Nil
    val exp13: DenseVector[Complex] = 1 :: -i :: -2 * i :: Nil
    println(exp11.t.getClass)
    assert(testMatrix.getRowVectorList == exp11.t.map(c => ~c) :: exp12.t.map(c => ~c) :: exp13.t.map(c => ~c) :: Nil)
    //    val exp21: DenseVector[Complex] = (1 + 16 * i :: 2 + 1 * i :: 1 :: Nil)
    //    val exp22: DenseVector[Complex] = (4 - 5 * i :: 14 - 5 * i :: -i :: Nil)
    //    val exp23: DenseVector[Complex] = (-2 :: -2 + i :: -2 * i :: Nil)
    //    assert(testMatrix.getColumnVectorList == exp21 :: exp22 :: exp23 :: Nil)
    //    val exp3: DenseVector[Complex] = (1 + 16 * i :: 4 - 5 * i :: -2 :: 2 + 1 * i :: 14 - 5 * i :: -2 + i :: 1 :: -i :: -2 * i :: Nil)
    //    testMatrix.flatten()
    //    assert(testMatrix.flatten(_) == exp3)
    //    println(testMatrix.normalized.flatten.sum)
  }
}
