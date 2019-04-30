package com.hwaipy.hydrogen.physics.polarization.muellermatrix

import Jama.Matrix

class MuellerMatrix(val matrix: Matrix) {
  def inverse = new MuellerMatrix(matrix.inverse())
}

object MuellerMatrix {
  def merge(matrixs: MuellerMatrix*) = new MuellerMatrix(matrixs.foldLeft(new Matrix(Array(Array(1, 0, 0, 0), Array(0, 1, 0, 0), Array(0, 0, 1, 0), Array(0, 0, 0, 1)))) { (a, b) => b.matrix.times(a) })
}

class Polarization(stokes: Matrix) extends Cloneable {
  def this(H: Double, D: Double, R: Double) {
    this(new Matrix(Array(Array(1), Array(H), Array(D), Array(R))))
  }

  def transform(muellerMatrix: MuellerMatrix) = new Polarization(muellerMatrix.matrix.times(stokes))

  override def clone() = new Polarization(stokes.get(1, 0), stokes.get(2, 0), stokes.get(3, 0))

  def getH = (1 + stokes.get(1, 0)) / 2

  def getV = (1 - stokes.get(1, 0)) / 2

  def getD = (1 + stokes.get(2, 0)) / 2

  def getA = (1 - stokes.get(2, 0)) / 2

  def getR = (1 + stokes.get(3, 0)) / 2

  def getL = (1 - stokes.get(3, 0)) / 2

  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("(").append(stokes.get(0, 0)).append(", ")
      .append(stokes.get(1, 0)).append(", ")
      .append(stokes.get(2, 0)).append(", ")
      .append(stokes.get(3, 0)).append(")")
    sb.toString()
  }
}

object Polarization {
  val H = new Polarization(1, 0, 0)
  val V = new Polarization(-1, 0, 0)
  val D = new Polarization(0, 1, 0)
  val A = new Polarization(0, -1, 0)
  val R = new Polarization(0, 0, 1)
  val L = new Polarization(0, 0, -1)
  val ZERO = new Polarization(0, 0, 0)
}

class WavePlate(val delay: Double, val theta: Double) extends MuellerMatrix(MuellerMatrixElement.wavePlate(delay, theta)) {
  def rotatedBy(delta: Double) = new WavePlate(delay, theta + delta)

  def rotated(target: Double) = new WavePlate(delay, target)
}

class HalfWavePlate(theta: Double) extends WavePlate(Math.PI, theta)

class QuarterWavePlate(theta: Double) extends WavePlate(Math.PI / 2, theta)

class Phase(phase: Double) extends WavePlate(phase, 0)

class Rotate(val theta: Double) extends MuellerMatrix(MuellerMatrixElement.rotate(theta))

object MuellerMatrixElement {
  def wavePlate(delay: Double, theta: Double) = {
    val theta2 = theta * 2
    val c2 = Math.cos(theta2)
    val s2 = Math.sin(theta2)
    val s2c2 = s2 * c2
    val cd = Math.cos(delay)
    val sd = Math.sin(delay)
    new Matrix(Array(
      Array(1, 0, 0, 0),
      Array(0, 1 - (1 - cd) * s2 * s2, (1 - cd) * s2c2, -sd * s2),
      Array(0, (1 - cd) * s2c2, 1 - (1 - cd) * c2 * c2, sd * c2),
      Array(0, sd * s2, -sd * c2, cd)))
  }

  def rotate(theta: Double) = {
    val theta2 = theta * 2
    val c2 = Math.cos(theta2)
    val s2 = Math.sin(theta2)
    new Matrix(Array(
      Array(1, 0, 0, 0),
      Array(0, c2, s2, 0),
      Array(0, -s2, c2, 0),
      Array(0, 0, 0, 1)))
  }
}
