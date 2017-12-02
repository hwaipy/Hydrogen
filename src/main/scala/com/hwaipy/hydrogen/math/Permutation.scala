package com.hwaipy.hydrogen.math

import java.util.concurrent.atomic.AtomicReference
import java.util.Arrays

object Permutation {
  //if not repeatable, returns (1,2,3),(1,2,4),(1,2,5),...
  //if repeatable, returns (1,1,1),(1,1,2),(1,1,3),...
  def iterator(size: Int, max: Int, min: Int = 1, repeatable: Boolean = false) = new PermutationIterable(size, max, min, repeatable).iterator
}

private class PermutationIterable(size: Int, private val max: Int, private val min: Int = 1, repeatable: Boolean = false) extends Iterable[List[Int]] {
  private val initial = repeatable match {
    case true => Range(0, size).map(a => min).toList
    case false => Range(0, size).map(a => min + a).toList
  }

  def iterator: Iterator[List[Int]] = new ArrangerIterator

  private class ArrangerIterator extends Iterator[List[Int]] {
    val nextItem = new AtomicReference(Option(initial))

    def hasNext = nextItem.get.isDefined

    def next = {
      if (!hasNext) throw new IllegalStateException("No more item.")
      val ret = nextItem.get.get
      repeatable match {
        case true => loadNextRepeatable
        case false => loadNextUnrepeatable
      }
      ret
    }

    private def loadNextUnrepeatable = {
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

    private def loadNextRepeatable = {
      val current = nextItem.get.get.toArray
      current(current.size - 1) += 1
      var carryI = current.size
      Range(current.size - 1, 0, -1).foreach(i => {
        if (current(i) > PermutationIterable.this.max) {
          current(i - 1) += 1
          carryI = i
        }
      })
      Range(carryI, current.size).foreach(i => {
        current(i) = current(i - 1)
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