package com.hwaipy.hydrogen.math

import breeze.math.Complex
import breeze.linalg.{Vector, DenseVector, DenseMatrix}
import scala.language.implicitConversions

object BreezeUtil {
  val i = Complex.i

  implicit class DoubleImp(d: Double) {
    def *(v: Vector[Complex]) = v * Complex(d, 0)

    def **(m: Double) = math.pow(d, m)
  }

  implicit class ComplexImp(c: Complex) {
    def unary_+ = c

    def unary_~ = c.conjugate

    def **(m: Double) = c pow m

    def **(m: Complex) = c pow m

    def =~(m: Complex)(implicit error: Double = 0.000000001) = {
      def approx(a: Double, b: Double) = math.abs(a - b) <= (math.max(math.abs(a), math.abs(b)) * error)

      approx(c.re, m.re) && approx(c.im, m.im)
    }

    def !=~(m: Complex)(implicit error: Double = 0.000000001) = !(c =~ m)

    def mod = c.abs

    def phase = math.atan2(c.im, c.re)

    def square = c.re ** 2 + c.im ** 2
  }

  implicit def fromDoubleToComplex(d: Double): Complex = Complex(d, 0)

  private def iterableToComplexVector(it: Iterable[Any]): DenseVector[Complex] = {
    val list = it.map({
      case a: Int => Complex(a, 0)
      case a: Double => Complex(a, 0)
      case a: Float => Complex(a, 0)
      case a: Long => Complex(a, 0)
      case a: Short => Complex(a, 0)
      case a: Byte => Complex(a, 0)
      case a: Complex => a
      case a => throw new IllegalArgumentException(s"$a is not a valid number.")
    }).toList
    DenseVector(list.toArray)
  }

  implicit def fromIterableAnyToComplexVector(it: Iterable[Any]): DenseVector[Complex] = iterableToComplexVector(it)

  implicit def fromIterableAnyToComplexMatrix(it: Iterable[Any]): DenseMatrix[Complex] = {
    def toCV(p: Any): DenseVector[Complex] = {
      p match {
        case p: Iterable[Any] => p
        case p: Vector[_] => iterableToComplexVector(Range(0, p.size).map(i => p(i)))
        case pp => throw new IllegalArgumentException(s"$p can not be recognized as a valid Complex Vector.")
      }
    }

    val vcs = it.map(l => toCV(l).toArray).toArray
    val rowNumber = vcs.size
    val columnNumbers = vcs.map(v => v.size)
    if (columnNumbers.max - columnNumbers.min != 0) {
      throw new IllegalArgumentException("The dimensions need to be identity to create a Matrix.")
    }
    if (columnNumbers.max == 0 || rowNumber == 0) {
      throw new IllegalArgumentException("The dimensions need to be greater than 0 to create a Matrix.")
    }
    val columnNumber = columnNumbers.max
    DenseMatrix.fill(rowNumber, columnNumber)(i).pairs.map(fn => vcs(fn._1._1)(fn._1._2))
  }

  implicit object mulDenseVector2IntImpl extends breeze.linalg.operators.OpMulMatrix.Impl2[DenseVector[Complex], Int, DenseVector[Complex]] {
    def apply(v: DenseVector[Complex], v2: Int): DenseVector[Complex] = v.map(_ * v2)
  }

  implicit object mulDenseVector2DoubleImpl extends breeze.linalg.operators.OpMulMatrix.Impl2[DenseVector[Complex], Double, DenseVector[Complex]] {
    def apply(v: DenseVector[Complex], v2: Double): DenseVector[Complex] = v.map(_ * v2)
  }

  implicit object divDenseVector2IntImpl extends breeze.linalg.operators.OpDiv.Impl2[DenseVector[Complex], Int, DenseVector[Complex]] {
    def apply(v: DenseVector[Complex], v2: Int): DenseVector[Complex] = v.map(_ / v2)
  }

  implicit object divDenseVector2DoubleImpl extends breeze.linalg.operators.OpDiv.Impl2[DenseVector[Complex], Double, DenseVector[Complex]] {
    def apply(v: DenseVector[Complex], v2: Double): DenseVector[Complex] = v.map(_ / v2)
  }

  implicit object mulComplex2DenseVectorImpl extends breeze.linalg.operators.OpMulMatrix.Impl2[Complex, DenseVector[Complex], DenseVector[Complex]] {
    def apply(v: Complex, v2: DenseVector[Complex]): DenseVector[Complex] = v2.map(_ * v)
  }

  implicit object mulDenseVector2ComplexComplexImpl extends breeze.linalg.operators.OpMulMatrix.Impl2[DenseVector[Complex], Complex, DenseVector[Complex]] {
    def apply(v: DenseVector[Complex], v2: Complex): DenseVector[Complex] = v.map(_ * v2)
  }

  implicit class ComplexVectorImp(v: DenseVector[Complex]) {
    def normalized = {
      val dev = math.sqrt(toList.map(c => c.square).sum)
      v.map(_ / dev)
    }

    def toList = v.toArray.toList

    def unary_+ = v

    def unary_~ = v.conjugate

    def conjugate = v.map(c => ~c)

    def square = (v dot ~v).re

    //    //def outer
  }

  implicit class ComplexMatrixImp(m: DenseMatrix[Complex]) {
    //    def foreach(action: (Complex, Int, Int) => Unit) = {
    //      Range(0, m.getNumberOfRows).foreach(row => Range(0, m.getNumberOfColumns).foreach(column => action(m.get(row, column), row, column)))
    //    }
    //
    //    def map(action: (Complex, Int, Int) => Complex) = {
    //      val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => action(m.get(row, column), row, column)).toArray).toArray
    //      ComplexMatrix.valueOf(data)
    //    }
    //

    def getRowVectorList = Range(0, m.rows).map(row => m(row, ::)).toList

    def getColumnVectorList = Range(0, m.cols).map(col => m(::, col)).toList

    //
    //    def getColumnVectorList = Range(0, m.getNumberOfColumns).map(col => m.getColumn(col)).toList
    //
    //    def flatten = getRowVectorList.map(r => r.toList).flatten
    //
    //    def normalized = {
    //      val items = flatten
    //      val sum = items.map(c => math.pow(c.magnitude, 2)).reduce { (a, b) => a + b }
    //      val c = math.sqrt(sum)
    //      m.times(1 / c)
    //    }
    //
    //    //
    //    //    def toList = {
    //    //      Range(0, v.getDimension).map(v.get(_)).toList
    //    //    }
    //    //
    //    //    def apply(i: Int) = v.get(i)
    //    //
    //    //    def sum = v.toList.reduce { (a, b) => a + b }
    //    //
    //    //    def unary_+ = v
    //    //
    //    //    def unary_- = v.times(-1)
    //    //
    //    //    def unary_~ = v.conjugate
    //    //
    //    //    def conjugate: ComplexVector = v.toList.map(c => ~c)
    //    //
    //    //    def +(v2: ComplexVector) = v plus v2
    //    //
    //    //    def -(v2: ComplexVector) = v minus v2
    //    //
    //    //    def *(v2: Complex) = v.times(v2)
    //    //
    //    //    def /(v2: Complex) = v.times(1 / v2)
    //    //
    //    //    def *(v2: ComplexVector) = v.times(v2)
    //    //
    //    //    def square = (v * ~v).getReal
    //
    //
    //    //  def mapOne(row: Int, column: Int, action: Complex => Complex) = {
    //    //    val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => m.get(row, column)).toArray).toArray
    //    //    data(row)(column) = action(data(row)(column))
    //    //    ComplexMatrix.valueOf(data)
    //    //  }
    //    //
    //
    //    def dag = m.map((c, row, column) => c.conjugate).transpose
    //
    //    //  def elementSum = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => m.get(row, column)).toList.fold(Complex.ZERO) { (a, b) => a.plus(b) }).toList.fold(Complex.ZERO) { (a, b) => a.plus(b) }
    //    //
    //    //  def unitarility = {
    //    //    val UdagUMod = m.times(m.dag).mapAll((c, row, column) => Complex.valueOf(c.magnitude, 0))
    //    //    //    val UdagUMod = m.dag.times(m).mapAll((c, row, column) => Complex.valueOf(c.magnitude, 0))
    //    //    val diag = ComplexMatrix.valueOf(UdagUMod.getDiagonal)
    //    //    val diagSum = diag.elementSum
    //    //    val UdagUModSum = UdagUMod.elementSum
    //    //    diagSum.getReal / UdagUModSum.getReal
    //    //  }
    //    //
    //    //  def shake = {
    //    //    val random = new Random
    //    //    m.mapOne(random.nextInt(m.getNumberOfRows), random.nextInt(m.getNumberOfColumns), c => {
    //    //      val move = random.nextGaussian * 0.1
    //    //      random.nextBoolean match {
    //    //        case true => Complex.valueOf(c.getReal + move, c.getImaginary)
    //    //        case false => Complex.valueOf(c.getReal, c.getImaginary + move)
    //    //      }
    //    //    })
    //    //  }
    //    //
    //    //  protected def subMatrixB(deleteIndex: Int, isRow: Boolean): ComplexMatrix = {
    //    //    val matrix = isRow match {
    //    //      case true => m
    //    //      case false => m.transpose
    //    //    }
    //    //    val m2 = ComplexMatrix.valueOf(Range(0, matrix.getNumberOfRows).filter(i => i != deleteIndex).map(i => matrix.getRow(i)))
    //    //    isRow match {
    //    //      case true => m2
    //    //      case false => m2.transpose
    //    //    }
    //    //  }
    //    //
    //    //  protected def subMatrix(deleteRow: Int, deleteColumn: Int): ComplexMatrix = {
    //    //    val m1 = m.subMatrixB(deleteRow, true)
    //    //    val m2 = m1.subMatrixB(deleteColumn, false)
    //    //    m2
    //    //  }
    //    //
    //    //  protected def doPerm: Complex = {
    //    //    m.getNumberOfRows match {
    //    //      case 0 => throw new RuntimeException
    //    //      case 1 => m.get(0, 0)
    //    //      case 2 => m.get(0, 0).times(m.get(1, 1)).plus(m.get(0, 1).times(m.get(1, 0)))
    //    //      case _ => {
    //    //        var p = Complex.ZERO
    //    //        for (i <- 0 until m.getNumberOfColumns) {
    //    //          val key = m.get(0, i)
    //    //          val subM = m.subMatrix(0, i)
    //    //          val subPerm = subM.doPerm
    //    //          p = p.plus(key.times(subPerm))
    //    //        }
    //    //        p
    //    //      }
    //    //    }
    //    //  }
    //    //
    //    //  protected def calculatePermenent(arrange: List[Int]) = {
    //    //    val subMode = m.getNumberOfRows
    //    //    val columns = arrange.map(cn => m.getColumn(cn - 1))
    //    //    val subM = ComplexMatrix.valueOf(columns).transpose
    //    //    val perm = subM.doPerm
    //    //    val permModSquare = math.pow(perm.getReal, 2) + math.pow(perm.getImaginary, 2)
    //    //    permModSquare
    //    //  }
    //    //
    //    //  protected def doCalculatePermenents = {
    //    //    val mode = m.getNumberOfColumns
    //    //    val subMode = m.getNumberOfRows
    //    //    val arranges = listArranges(1, mode + 1, subMode)
    //    //    val perms = arranges.map(m.calculatePermenent)
    //    //    val sum = perms.reduce { (a, b) => a + b }
    //    //    val permsNorm = perms.map(p => p / sum)
    //    //    permsNorm
    //    //  }
    //    //
    //    //  def permenents(inputs: List[Int]) = {
    //    //    val rows = inputs.map(i => m.getRow(i))
    //    //    val subMode = rows.size
    //    //    val permM = ComplexMatrix.valueOf(rows)
    //    //    val permenents = permM.doCalculatePermenents
    //    //    permenents
    //    //  }
  }

}