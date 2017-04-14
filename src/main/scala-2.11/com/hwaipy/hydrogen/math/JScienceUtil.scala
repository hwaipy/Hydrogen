package com.hwaipy.hydrogen.math

import org.jscience.mathematics.number.Complex
import org.jscience.mathematics.vector.ComplexMatrix
import org.jscience.mathematics.vector.ComplexVector

object JScienceUtil {

  implicit class ComplexImp(c: Complex) {
  }

  implicit class ComplexMatrixImp(m: ComplexMatrix) {
    //  def mapOne(row: Int, column: Int, action: Complex => Complex) = {
    //    val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => m.get(row, column)).toArray).toArray
    //    data(row)(column) = action(data(row)(column))
    //    ComplexMatrix.valueOf(data)
    //  }
    //
    def foreach(action: (Complex, Int, Int) => Unit) = {
      Range(0, m.getNumberOfRows).foreach(row => Range(0, m.getNumberOfColumns).foreach(column => action(m.get(row, column), row, column)))
    }

    def map(action: (Complex, Int, Int) => Complex) = {
      val data = Range(0, m.getNumberOfRows).map(row => Range(0, m.getNumberOfColumns).map(column => action(m.get(row, column), row, column)).toArray).toArray
      ComplexMatrix.valueOf(data)
    }

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
    //
    //  def normalRow = {
    //    val rows = Range(0, m.getNumberOfRows).map(m.getRow(_).normal)
    //    ComplexMatrix.valueOf(rows)
    //  }
  }

  implicit class ComplexVectorImp(v: ComplexVector) {
    //  def normal = {
    //    val items = toList
    //    val sum = items.map(c => math.pow(c.magnitude, 2)).reduce { (a, b) => a + b }
    //    val normaledItems = items.map(c => c.divide(math.sqrt(sum)))
    //    ComplexVector.valueOf(normaledItems)
    //  }
    //
    //  def toList = {
    //    Range(0, v.getDimension).map(v.get(_)).toList
    //  }
  }

}