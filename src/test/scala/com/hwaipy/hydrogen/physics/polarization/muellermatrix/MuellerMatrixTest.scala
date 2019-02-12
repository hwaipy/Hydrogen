package com.hwaipy.hydrogen.math

import org.scalatest._
import com.hwaipy.hydrogen.physics.polarization.muellermatrix._

import scala.util.Random

class MuellerMatrixTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {

  override def beforeAll() {
  }

  override def afterAll() {
  }

  before {
  }

  after {
  }

  test("Test MuellerMatrix and Merge.") {
    assert(Polarization.H.transform(new HalfWavePlate(0)).getH == 1)
    assert(Polarization.H.transform(new HalfWavePlate(math.Pi / 4)).getH == 0)
    assert(Polarization.H.transform(new QuarterWavePlate(math.Pi / 4)).getR == 1)
    assert(Polarization.H.transform(new QuarterWavePlate(-math.Pi / 4)).getR == 0)
    assert(Polarization.H.transform(new QuarterWavePlate(-math.Pi / 4)).transform(new QuarterWavePlate(-math.Pi / 4)).getH == 0)
    assert(Polarization.H.transform(MuellerMatrix.merge(new QuarterWavePlate(-math.Pi / 4), new QuarterWavePlate(-math.Pi / 4))).getH == 0)
  }

  test("Test M1Process.") {
    val bbMatrix = MuellerMatrix.merge(new HalfWavePlate(0.1), new Phase(1), new QuarterWavePlate(-2), new Rotate(0.5))
    val result = M1Process.calculate(bbMatrix)
    assert(Polarization.H.transform(MuellerMatrix.merge(bbMatrix, new QuarterWavePlate(result(0)), new QuarterWavePlate(result(1)), new HalfWavePlate(result(2)))).getH == 1)
  }

  test("Test M1Process with random.") {
    val random = new Random()
    (0 to 10000).foreach(_ => {
      val bbMatrix = MuellerMatrix.merge(new QuarterWavePlate(random.nextDouble * math.Pi * 2), new HalfWavePlate(random.nextDouble * math.Pi * 2), new QuarterWavePlate(random.nextDouble * math.Pi * 2), new Rotate(random.nextDouble * math.Pi * 2), new Phase(random.nextDouble * math.Pi * 2))
      val result = M1Process.calculate(bbMatrix)
      assert(math.abs(Polarization.H.transform(MuellerMatrix.merge(bbMatrix, new QuarterWavePlate(result(0)), new QuarterWavePlate(result(1)), new HalfWavePlate(result(2)))).getH - 1) < 0.0000000001)
    })
  }

  test("Test AnnealingProcess with random.") {
    val random = new Random()
    (0 to 10).foreach(_ => {
      val bbMatrix = MuellerMatrix.merge(new QuarterWavePlate(random.nextDouble * math.Pi * 2), new HalfWavePlate(random.nextDouble * math.Pi * 2), new QuarterWavePlate(random.nextDouble * math.Pi * 2), new Rotate(random.nextDouble * math.Pi * 2), new Phase(random.nextDouble * math.Pi * 2))
      val target = M1Process.calculate(bbMatrix)
      val startPoints = target.map(_ + 0 / 180 * 3)
      val result = new AnnealingProcess(0.9999, 4048, startPoints.toArray, target.toArray).process._1
      assert(math.abs(Polarization.H.transform(MuellerMatrix.merge(bbMatrix, new QuarterWavePlate(result(0)), new QuarterWavePlate(result(1)), new HalfWavePlate(result(2)))).getH - 1) < 2e-4)
    })
  }
}