package com.hwaipy.hydrogen.math

object ScienceUtil {

  implicit class dBImpDouble(d: Double) {
    def dB = math.pow(10, -0.1 * d)
  }

  implicit class dBImpInt(d: Int) {
    def dB = math.pow(10, -0.1 * d)
  }

}
