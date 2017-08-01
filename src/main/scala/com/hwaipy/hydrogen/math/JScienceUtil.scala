package com.hwaipy.hydrogen.math

import org.jscience.mathematics.number.Complex
import org.jscience.mathematics.vector.{ComplexMatrix, ComplexVector}
import scala.collection.JavaConverters._
import scala.collection.Iterable
import scala.language.implicitConversions

object JScienceUtil {
  val i = Complex.I
  val I = Complex.I

  implicit def fromDouble(v: Double): Complex = Complex.valueOf(v, 0)

  implicit def fromFloat(v: Float): Complex = Complex.valueOf(v, 0)

  implicit def fromLong(v: Long): Complex = Complex.valueOf(v, 0)

  implicit def fromInt(v: Int): Complex = Complex.valueOf(v, 0)

  implicit def fromShort(v: Short): Complex = Complex.valueOf(v, 0)

  implicit def fromByte(v: Byte): Complex = Complex.valueOf(v, 0)

  implicit class ComplexImp(c: Complex) {
    def unary_+ = c

    def unary_- = 0 - c

    def unary_~ = c.conjugate()

    def +(v: Complex) = c plus v

    def -(v: Complex) = c minus v

    def *(v: Complex) = c times v

    def **(m: Double) = c pow m

    def **(m: Complex) = c pow m

    def /(v: Complex) = c divide v

    def *(v: ComplexVector) = v times c

    def mod = c.magnitude

    def phase = c.argument

    def square = c.getReal ** 2 + c.getImaginary ** 2

  }

  implicit class ComplexImpDouble(d: Double) {

    def +(v: Complex) = v plus d

    def -(v: Complex) = d minus v

    def *(v: Complex) = d times v

    def /(v: Complex) = d divide v

    def **(m: Double) = math.pow(d, m)

    def *(v: ComplexVector) = v times d
  }

  implicit def fromIterableAnyToComplexVector(it: Iterable[Any]): ComplexVector = {
    val list = it.map(i => i match {
      case a: Int => Complex.valueOf(a, 0)
      case a: Double => Complex.valueOf(a, 0)
      case a: Float => Complex.valueOf(a, 0)
      case a: Long => Complex.valueOf(a, 0)
      case a: Short => Complex.valueOf(a, 0)
      case a: Byte => Complex.valueOf(a, 0)
      case a: Complex => a
      case a => throw new IllegalArgumentException(s"$a is not a valid number.")
    }).toList.asJava
    ComplexVector.valueOf(list)
  }

  implicit def fromIterableAnyToComplexMatrix(it: Iterable[Any]): ComplexMatrix = {
    def toCV(p: Any): ComplexVector = {
      p match {
        case p: Iterable[Any] => p
        case p: ComplexVector => p
        case p => throw new IllegalArgumentException(s"$p can not be recognized as a valid Complex Vector.")
      }
    }
    val vcs = it.map(l => toCV(l)).toList
    ComplexMatrix.valueOf(vcs.asJava)
  }

  implicit class ComplexVectorImp(v: ComplexVector) {
    def normalized = {
      val items = toList
      val sum = items.map(c => math.pow(c.magnitude, 2)).reduce { (a, b) => a + b }
      val normaledItems = items.map(c => c.divide(math.sqrt(sum)))
      ComplexVector.valueOf(normaledItems)
    }

    def toList = {
      Range(0, v.getDimension).map(v.get(_)).toList
    }

    def apply(i: Int) = v.get(i)

    def sum = v.toList.reduce { (a, b) => a + b }

    def unary_+ = v

    def unary_- = v.times(-1)

    def unary_~ = v.conjugate

    def conjugate: ComplexVector = v.toList.map(c => ~c)

    def +(v2: ComplexVector) = v plus v2

    def -(v2: ComplexVector) = v minus v2

    def *(v2: Complex) = v.times(v2)

    def /(v2: Complex) = v.times(1 / v2)

    def *(v2: ComplexVector) = v.times(v2)

    def square = (v * ~v).getReal

    //def outer
  }

  implicit class ComplexMatrixImp(m: ComplexMatrix) {
    def foreach(action: (Complex, Int, Int) => Unit) = {
      Range(0, m.getNumberOfRows).foreach(row => Range(0, m.getNumberOfColumns).foreach(column => action(m.get(row, column), row, column)))
    }

    def map(action: (Complex, Int, Int) => Complex) = {
      val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => action(m.get(row, column), row, column)).toArray).toArray
      ComplexMatrix.valueOf(data)
    }

    def getRowVectorList = Range(0, m.getNumberOfRows).map(row => m.getRow(row)).toList

    def getColumnVectorList = Range(0, m.getNumberOfColumns).map(col => m.getColumn(col)).toList

    def flatten = getRowVectorList.map(r => r.toList).flatten

    def normalized = {
      val items = flatten
      val sum = items.map(c => math.pow(c.magnitude, 2)).reduce { (a, b) => a + b }
      val c = math.sqrt(sum)
      m.times(1 / c)
    }

    //
    //    def toList = {
    //      Range(0, v.getDimension).map(v.get(_)).toList
    //    }
    //
    //    def apply(i: Int) = v.get(i)
    //
    //    def sum = v.toList.reduce { (a, b) => a + b }
    //
    //    def unary_+ = v
    //
    //    def unary_- = v.times(-1)
    //
    //    def unary_~ = v.conjugate
    //
    //    def conjugate: ComplexVector = v.toList.map(c => ~c)
    //
    //    def +(v2: ComplexVector) = v plus v2
    //
    //    def -(v2: ComplexVector) = v minus v2
    //
    //    def *(v2: Complex) = v.times(v2)
    //
    //    def /(v2: Complex) = v.times(1 / v2)
    //
    //    def *(v2: ComplexVector) = v.times(v2)
    //
    //    def square = (v * ~v).getReal


    //  def mapOne(row: Int, column: Int, action: Complex => Complex) = {
    //    val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => m.get(row, column)).toArray).toArray
    //    data(row)(column) = action(data(row)(column))
    //    ComplexMatrix.valueOf(data)
    //  }
    //

    def dag = m.map((c, row, column) => c.conjugate).transpose

    //  def elementSum = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => m.get(row, column)).toList.fold(Complex.ZERO) { (a, b) => a.plus(b) }).toList.fold(Complex.ZERO) { (a, b) => a.plus(b) }
    //
    //  def unitarility = {
    //    val UdagUMod = m.times(m.dag).mapAll((c, row, column) => Complex.valueOf(c.magnitude, 0))
    //    //    val UdagUMod = m.dag.times(m).mapAll((c, row, column) => Complex.valueOf(c.magnitude, 0))
    //    val diag = ComplexMatrix.valueOf(UdagUMod.getDiagonal)
    //    val diagSum = diag.elementSum
    //    val UdagUModSum = UdagUMod.elementSum
    //    diagSum.getReal / UdagUModSum.getReal
    //  }
    //
    //  def shake = {
    //    val random = new Random
    //    m.mapOne(random.nextInt(m.getNumberOfRows), random.nextInt(m.getNumberOfColumns), c => {
    //      val move = random.nextGaussian * 0.1
    //      random.nextBoolean match {
    //        case true => Complex.valueOf(c.getReal + move, c.getImaginary)
    //        case false => Complex.valueOf(c.getReal, c.getImaginary + move)
    //      }
    //    })
    //  }
    //
    //  protected def subMatrixB(deleteIndex: Int, isRow: Boolean): ComplexMatrix = {
    //    val matrix = isRow match {
    //      case true => m
    //      case false => m.transpose
    //    }
    //    val m2 = ComplexMatrix.valueOf(Range(0, matrix.getNumberOfRows).filter(i => i != deleteIndex).map(i => matrix.getRow(i)))
    //    isRow match {
    //      case true => m2
    //      case false => m2.transpose
    //    }
    //  }
    //
    //  protected def subMatrix(deleteRow: Int, deleteColumn: Int): ComplexMatrix = {
    //    val m1 = m.subMatrixB(deleteRow, true)
    //    val m2 = m1.subMatrixB(deleteColumn, false)
    //    m2
    //  }
    //
    //  protected def doPerm: Complex = {
    //    m.getNumberOfRows match {
    //      case 0 => throw new RuntimeException
    //      case 1 => m.get(0, 0)
    //      case 2 => m.get(0, 0).times(m.get(1, 1)).plus(m.get(0, 1).times(m.get(1, 0)))
    //      case _ => {
    //        var p = Complex.ZERO
    //        for (i <- 0 until m.getNumberOfColumns) {
    //          val key = m.get(0, i)
    //          val subM = m.subMatrix(0, i)
    //          val subPerm = subM.doPerm
    //          p = p.plus(key.times(subPerm))
    //        }
    //        p
    //      }
    //    }
    //  }
    //
    //  protected def calculatePermenent(arrange: List[Int]) = {
    //    val subMode = m.getNumberOfRows
    //    val columns = arrange.map(cn => m.getColumn(cn - 1))
    //    val subM = ComplexMatrix.valueOf(columns).transpose
    //    val perm = subM.doPerm
    //    val permModSquare = math.pow(perm.getReal, 2) + math.pow(perm.getImaginary, 2)
    //    permModSquare
    //  }
    //
    //  protected def doCalculatePermenents = {
    //    val mode = m.getNumberOfColumns
    //    val subMode = m.getNumberOfRows
    //    val arranges = listArranges(1, mode + 1, subMode)
    //    val perms = arranges.map(m.calculatePermenent)
    //    val sum = perms.reduce { (a, b) => a + b }
    //    val permsNorm = perms.map(p => p / sum)
    //    permsNorm
    //  }
    //
    //  def permenents(inputs: List[Int]) = {
    //    val rows = inputs.map(i => m.getRow(i))
    //    val subMode = rows.size
    //    val permM = ComplexMatrix.valueOf(rows)
    //    val permenents = permM.doCalculatePermenents
    //    permenents
    //  }
  }

}


//case class Complex(re: Double, im: Double) extends Ordered[Complex] {
//  private val modulus = sqrt(pow(re, 2) + pow(im, 2))
//
//  // Constructors
//  def this(re: Double) = this(re, 0)
//
//  // Unary operators
//  def unary_+ = this
//
//  def unary_- = new Complex(-re, -im)
//
//  def unary_~ = new Complex(re, -im)
//
//  // conjugate
//  def unary_! = modulus
//
//  // Comparison
//  def compare(that: Complex) = !this compare !that
//
//  // Arithmetic operations
//  def +(c: Complex) = new Complex(re + c.re, im + c.im)
//
//  def -(c: Complex) = this + -c
//
//  def *(c: Complex) =
//    new Complex(re * c.re - im * c.im, im * c.re + re * c.im)
//
//  def /(c: Complex) = {
//    require(c.re != 0 || c.im != 0)
//    val d = pow(c.re, 2) + pow(c.im, 2)
//    new Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
//  }
//
//  // String representation
//  override def toString() =
//  this match {
//    case Complex.i => "i"
//    case Complex(re, 0) => re.toString
//    case Complex(0, im) => im.toString + "*i"
//    case _ => asString
//  }
//
//  private def asString =
//    re + (if (im < 0) "-" + -im else "+" + im) + "*i"
//}
//
//object Complex {
//  // Constants
//  val i = new Complex(0, 1)
//
//  // Factory methods
//  def apply(re: Double) = new Complex(re)
//
//  // Implicit conversions
//  implicit def fromDouble(d: Double) = new Complex(d)
//
//  implicit def fromFloat(f: Float) = new Complex(f)
//
//  implicit def fromLong(l: Long) = new Complex(l)
//
//  implicit def fromInt(i: Int) = new Complex(i)
//
//  implicit def fromShort(s: Short) = new Complex(s)
//}