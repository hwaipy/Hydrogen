/*
package com.hwaipy.hydrogen.io.filesystem

import java.nio.file._
import java.util.concurrent.TimeUnit
import collection.JavaConversions._
import com.sun.nio.file.SensitivityWatchEventModifier
import org.scalatest._
import PathImplicit._

class WatchServiceUtilsTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {
  private val testPath = Paths.get("target/testregion/watchserviceutil")

  override def beforeAll() {
    Files.createDirectories(testPath)
    Files.createDirectories(testPath.resolve("fold1"))
    Files.createFile(testPath.resolve("file1"))
    Files.createDirectories(testPath.resolve("fold1/fold11"))
    Files.createFile(testPath.resolve("fold1/file1"))
    Files.createFile(testPath.resolve("fold1/fold11/file1"))
  }

  override def afterAll() {
    def clearPath(path: Path) {
      if (Files.isDirectory(path)) {
        Files.list(path).toArray.foreach(a => clearPath(a.asInstanceOf[Path]))
      }
      Files.delete(path)
    }
    clearPath(testPath.getParent)
  }

  before {
  }

  private def assertWatchEvent(watchEvent: WatchEvent[_], kind: WatchEvent.Kind[_], count: Int, context: Path) {
    assert(watchEvent.kind == kind)
    assert(watchEvent.count == count)
    assert(watchEvent.context == context)
  }

  //  test("Test the usage of WatchService API.") {
  //    val watchService = FileSystems.getDefault.newWatchService
  //    testPath.register(watchService, Array[WatchEvent.Kind[_]](StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY), SensitivityWatchEventModifier.HIGH)
  //
  //    Files.createFile(testPath.resolve("NewFile1"))
  //    val key1 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key1 != null)
  //    val events1 = key1.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events1(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewFile1"))
  //    key1.reset
  //
  //    Files.createDirectories(testPath.resolve("NewDir1"))
  //    val key2 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key2 != null)
  //    val events2 = key2.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events2(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewDir1"))
  //    key2.reset
  //
  //    val writer1 = Files.newBufferedWriter(testPath.resolve("NewFile1"))
  //    writer1.write("testcontext", 0, 10)
  //    writer1.close
  //    val key3 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key3 != null)
  //    val events3 = key3.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events3(0), StandardWatchEventKinds.ENTRY_MODIFY, 1, Paths.get("NewFile1"))
  //    key3.reset
  //
  //    Files.move(testPath.resolve("NewFile1"), testPath.resolve("NewFile1NewName"))
  //    val key4 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key4 != null)
  //    val events4 = key4.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events4(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewFile1NewName"))
  //    assertWatchEvent(events4(1), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewFile1"))
  //    key4.reset
  //
  //    Files.delete(testPath.resolve("NewFile1NewName"))
  //    val key5 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key5 != null)
  //    val events5 = key5.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events5(0), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewFile1NewName"))
  //    key5.reset
  //
  //    Files.move(testPath.resolve("NewDir1"), testPath.resolve("NewDir1NewName"))
  //    val key6 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key6 != null)
  //    val events6 = key6.pollEvents().filter(e => e.context() != ".DS_Store")
  //    assertWatchEvent(events6(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewDir1NewName"))
  //    assertWatchEvent(events6(1), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewDir1"))
  //    key6.reset
  //
  //    Files.createFile(testPath.resolve("fold1/fold11/filenotmonited"))
  //    val key7 = watchService.poll(5, TimeUnit.SECONDS)
  //    assert(key7 == null)
  //  }
  /*
    test("Test the usage of registerAll.") {
      val watchService = FileSystems.getDefault.newWatchService
      testPath.registerAll(watchService, Array[WatchEvent.Kind[_]](StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY), SensitivityWatchEventModifier.HIGH)

      Files.createFile(testPath.resolve("NewFile1"))
      val key1 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key1 != null)
      val events1 = key1.pollEvents().filter(e => e.context() != ".DS_Store")
      assertWatchEvent(events1(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewFile1"))
      key1.reset

      Files.createFile(testPath.resolve("fold1/fold11/file2"))
      val key2 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key2 != null)
      val events2 = key2.pollEvents().filter(e => e.context() != ".DS_Store")
      assertWatchEvent(events2(0), StandardWatchEventKinds.ENTRY_MODIFY, 1, Paths.get("fold11"))
      key2.reset
      val key3 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key3 != null)
      val events3 = key3.pollEvents().filter(e => e.context() != ".DS_Store")
      assertWatchEvent(events3(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("file2"))
      key3.reset

      Files.move(testPath.resolve("fold1"), testPath.resolve("fold1NewName"))
      val key4 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key4 != null)
      val events4 = key4.pollEvents().filter(e => e.context() != ".DS_Store")
      //    assertWatchEvent(events4(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewFile1NewName"))
      //    assertWatchEvent(events4(1), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewFile1"))
      key4.reset
      val key5 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key5 != null)
      val events5 = key5.pollEvents().filter(e => e.context() != ".DS_Store")
      //    assertWatchEvent(events5(0), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewFile1NewName"))
      key5.reset
      println(key5.isValid)
      val key6 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key6 != null)
      val events6 = key6.pollEvents().filter(e => e.context() != ".DS_Store")
      //    assertWatchEvent(events6(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewDir1NewName"))
      //    assertWatchEvent(events6(1), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewDir1"))
      println(key6)
      key6.reset

      val key7 = watchService.poll(5, TimeUnit.SECONDS)
      assert(key7 != null)
      val events7 = key7.pollEvents().filter(e => e.context() != ".DS_Store")
      //    assertWatchEvent(events6(0), StandardWatchEventKinds.ENTRY_CREATE, 1, Paths.get("NewDir1NewName"))
      //    assertWatchEvent(events6(1), StandardWatchEventKinds.ENTRY_DELETE, 1, Paths.get("NewDir1"))
      println(key7)
      key7.reset


      println(4)
      events4.foreach(e => println(s"E: ${e.kind()}, ${e.count()}, ${e.context()}"))
      println(5)
      events5.foreach(e => println(s"E: ${e.kind()}, ${e.count()}, ${e.context()}"))
      println(6)
      events6.foreach(e => println(s"E: ${e.kind()}, ${e.count()}, ${e.context()}"))

    }*/

  //  events4.foreach(e => println(s"E: ${e.kind()}, ${e.count()}, ${e.context()}"))
}

*/
