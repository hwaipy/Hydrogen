package com.hwaipy.hydrogen.image.gif

import java.awt.image.BufferedImage

object GIFBuilder {
  def newBuilder(outputDir: String) = new GIFBuilder(outputDir)
}

class GIFBuilder private(outputDir: String) {
  private val animatedGifEncoder = new AnimatedGifEncoder
  animatedGifEncoder.start(outputDir)

  def repeat(r: Int) = {
    animatedGifEncoder.setRepeat(r)
    this
  }

  def addFrame(image: BufferedImage, delay: Int) = {
    animatedGifEncoder.setDelay(delay)
    animatedGifEncoder.addFrame(image)
    this
  }

  def addFrames(images: Iterable[BufferedImage], delay: Int) = {
    images.foreach(image => {
      addFrame(image, delay)
    })
    this
  }

  def addFrames(images: Iterable[BufferedImage], delays: Iterable[Int]) = {
    images.zip(delays).foreach(zip => {
      addFrame(zip._1, zip._2)
    })
    this
  }

  def finish {
    animatedGifEncoder.finish
  }
}
