package adt

// Most common test packages
import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class TestArrayQueue {
    // Sets default value for queue, should be added to annotation @Before
    var q: ArrayQueue[Int] = null

    // Will run before all other tests, setting q equal to empty queue
    @Before def createQueue = {
        q = new ArrayQueue[Int]
    }

    // Notation indicates it is a test
    @Test def emptyOnNew(): Unit = {
        assertTrue(q.isEmpty)
    }

    @Test def addRemoveOne(): Unit = {
        q.enqueue(5)
        assertFalse(q.isEmpty)
        assertEquals(5, q.peek)
        assertEquals(5, q.dequeue())
        assertTrue(q.isEmpty)
    }
    
    @Test def addRemoveLots(): Unit = {
        val nums = Array.fill(100)(util.Random.nextInt(100))
        for (n <- nums) q.enqueue(n)
        for (n <- nums) {
            assertEquals(n, q.peek)
            assertEquals(n, q.dequeue())
        }
    }
}