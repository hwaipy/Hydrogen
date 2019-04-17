package com.hwaipy.hydrogen.physics.polarization.muellermatrix

import Jama.Matrix

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object M1Process {
  def calculate(bbMatrix: MuellerMatrix) = {
    val qwp1 = new QuarterWavePlate(0)
    val qwp2 = new QuarterWavePlate(0)
    val qwp2p = new QuarterWavePlate(-Math.PI / 4)
    val hwp = new HalfWavePlate(0)
    val hwpp = new HalfWavePlate(-Math.PI / 8)

    val measurementH1 = Polarization.H.transform(bbMatrix).transform(qwp1).transform(qwp2).transform(hwp)
    val mHH = measurementH1.getH
    val mHV = measurementH1.getV
    val mHD = measurementH1.getD
    val mHA = measurementH1.getA
    val measurementD1 = Polarization.D.transform(bbMatrix).transform(qwp1).transform(qwp2).transform(hwp)
    val mDH = measurementD1.getH
    val mDV = measurementD1.getV
    val mDD = measurementD1.getD
    val mDA = measurementD1.getA
    val measurementH2 = Polarization.H.transform(bbMatrix).transform(qwp1).transform(qwp2p).transform(hwpp)
    val mHL = measurementH2.getH
    val mHR = measurementH2.getV
    val measurementD2 = Polarization.D.transform(bbMatrix).transform(qwp1).transform(qwp2p).transform(hwpp)
    val mDL = measurementD2.getH
    val mDR = measurementD2.getV

    new M1Process(List(mHH, mHV, mHD, mHA, mHL, mHR, mDH, mDV, mDD, mDA, mDL, mDR)).calculate
  }
}

class M1Process(inputs: List[Double]) {
  if (inputs.size != 12) {
    throw new IllegalArgumentException();
  }
  val IHH = inputs(0)
  val IHV = inputs(1)
  val IHD = inputs(2)
  val IHA = inputs(3)
  val IHL = inputs(4)
  val IHR = inputs(5)
  val IDH = inputs(6)
  val IDV = inputs(7)
  val IDD = inputs(8)
  val IDA = inputs(9)
  val IDL = inputs(10)
  val IDR = inputs(11)

  def calculate = {
    val sh = Array((IHH - IHV) / (IHH + IHV), (IHD - IHA) / (IHD + IHA), (IHR - IHL) / (IHR + IHL))
    val sd = Array((IDH - IDV) / (IDH + IDV), (IDD - IDA) / (IDD + IDA), (IDR - IDL) / (IDR + IDL))
    val sr = Array(sh(1) * sd(2) - sh(2) * sd(1), sh(2) * sd(0) - sh(0) * sd(2), sh(0) * sd(1) - sh(1) * sd(0))
    val m = Math.sqrt(Math.pow(sr(0), 2) + Math.pow(sr(1), 2) + Math.pow(sr(2), 2))
    val SH = new Matrix(Array(Array(1, sh(0), sh(1), sh(2)))).transpose()
    val SR = new Matrix(Array(Array(1, sr(0) / m, sr(1) / m, sr(2) / m))).transpose()
    val theta1 = 0.5 * Math.atan(SR.get(2, 0) / SR.get(1, 0))

    val qwp1 = new QuarterWavePlate(theta1)
    val SR1 = qwp1.matrix.times(SR)
    val SH1 = qwp1.matrix.times(SH)

    val a2 = 0.5 * Math.atan(SH1.get(2, 0) / SH1.get(1, 0))
    val a3 = 0.5 * Math.asin(SH1.get(3, 0))
    val results = new ArrayBuffer[Double]()
    results += theta1
    var flag = 0
    if (SH1.get(1, 0) > 0) flag |= 1 << 4
    if (SH1.get(2, 0) > 0) flag |= 1 << 3
    if (SH1.get(3, 0) > 0) flag |= 1 << 2
    if (SR1.get(1, 0) > 0) flag |= 1 << 1
    if (SR1.get(2, 0) > 0) flag |= 1
    flag match {
      case f if List(23, 19, 29, 25).contains(f) => {
        results += a2
        results += (a2 + a3) / 2
      }
      case f if List(15).contains(f) => {
        results += a2
        results += (a2 - a3) / 2 + Math.PI / 4
      }
      case f if List(11, 5, 1).contains(f) => {
        results += a2
        val result2 = (a2 - a3) / 2
        results += (if (result2 > 0) (result2 - Math.PI / 4) else (result2 + Math.PI / 4))
      }
      case f if List(20, 16, 30, 26).contains(f) => {
        results += a2 + Math.PI / 2
        results += (a2 - a3) / 2
      }
      case f if List(6).contains(f) => {
        results += a2 + Math.PI / 2
        results += (a2 + a3) / 2 - Math.PI / 4
      }
      case f if List(8).contains(f) => {
        results += a2 + Math.PI / 2
        results += (a2 + a3) / 2 + Math.PI / 4
      }
      case f if List(12, 2).contains(f) => {
        results += a2 + Math.PI / 2
        val result2 = (a2 + a3) / 2
        results += (if (result2 > 0) (result2 - Math.PI / 4) else (result2 + Math.PI / 4))
      }
      case _ => throw new IllegalStateException()
    }
    results.toList
  }
}

class AnnealingProcess(r: Double, jC: Double, startPoint: Array[Double], target: Array[Double]) {
  val ft = MuellerMatrix.merge(new HalfWavePlate(target(2) + Math.PI / 2), new QuarterWavePlate(target(1) + Math.PI / 2), new QuarterWavePlate(target(0) + Math.PI / 2))
  val wavePlates = Array(new QuarterWavePlate(startPoint(0)), new QuarterWavePlate(startPoint(1)), new HalfWavePlate(startPoint(2)))
  val random = new Random()

  def process = {
    var T = 6000.0
    val Tmin = 25.0
    var lastJ = J
    var currentJ = J
    while (T > Tmin) {
      move
      lastJ = currentJ
      currentJ = J
      val dE = currentJ - lastJ
      if (dE > 0) {
      } else if (Math.exp((dE / T)) > random.nextDouble()) {
      } else {
        rollback
      }
      T = r * T
    }
    (wavePlates.map(_.theta), jointContrastDB)
  }

  private val preservedThetas = wavePlates.map(_.theta)

  private def move = {
    (0 until 3).foreach(i => preservedThetas(i) = wavePlates(i).theta)
    var indexOfMove = random.nextInt(3)
    val stepLength = (random.nextDouble() - 0.5) / 10 / 180 * Math.PI
    wavePlates(indexOfMove) = wavePlates(indexOfMove).rotatedBy(stepLength)
  }

  private def rollback = (0 until 3).foreach(i => wavePlates(i) = wavePlates(i).rotated(preservedThetas(i)))

  private def J = jointContrastDB * jC

  private def jointContrastDB = Math.log10(jointContrast) * 10

  private def jointContrast = {
    val powers = readPowers
    val contrastH = powers(0) / powers(1)
    val contrastD = powers(6) / powers(7)
    val v = math.sqrt(1 / contrastH / contrastH + 1 / contrastD / contrastD)
    1 / v
  }

  private def readPowers = {
    val measurementH = Polarization.H.transform(ft).transform(wavePlates(0)).transform(wavePlates(1)).transform(wavePlates(2))
    val measurementD = Polarization.D.transform(ft).transform(wavePlates(0)).transform(wavePlates(1)).transform(wavePlates(2))
    List(measurementH.getH, measurementH.getV, measurementH.getD, measurementH.getA, measurementD.getH, measurementD.getV, measurementD.getD, measurementD.getA)
  }
}