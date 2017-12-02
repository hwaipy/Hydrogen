package com.hwaipy.hydrogen.math

import java.util.concurrent.atomic.AtomicReference
import java.util.Arrays

object Permutation {
  def iterator(size: Int, max: Int, min: Int = 1) = new PermutationIterable(size, max, min).iterator
}

private class PermutationIterable(size: Int, private val max: Int, private val min: Int = 1) extends Iterable[List[Int]] {
  private val initial = Range(0, size).map(a => min + a).toList

  def iterator: Iterator[List[Int]] = new ArrangerIterator

  private class ArrangerIterator extends Iterator[List[Int]] {
    val nextItem = new AtomicReference(Option(initial))

    def hasNext = nextItem.get.isDefined

    def next = {
      if (!hasNext) throw new IllegalStateException("No more item.")
      val ret = nextItem.get.get
      loadNext
      ret
    }

    private def loadNext = {
      val current = nextItem.get.get.toArray
      current(current.size - 1) += 1
      var carryI = current.size
      Range(current.size - 1, 0, -1).foreach(i => {
        if (current(i) > PermutationIterable.this.max - (current.size - 1 - i)) {
          current(i - 1) += 1
          carryI = i
        }
      })
      Range(carryI, current.size).foreach(i => {
        current(i) = current(i - 1) + 1
      })
      if (current(current.size - 1) > PermutationIterable.this.max) {
        nextItem.set(None)
      }
      else {
        nextItem.set(Option(current.toList))
      }
    }
  }

}