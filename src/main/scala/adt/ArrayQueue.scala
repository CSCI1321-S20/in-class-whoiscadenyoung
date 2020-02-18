package adt

import scala.reflect.ClassTag

class ArrayQueue[A: ClassTag] extends Queue[A] {
    // Private var because it needs to get a new size if it exceeds current size
    // For null, set to instance of A, however need to specify ClassTag because it tells it what default of type is
    private var data = Array.fill(10)(null.asInstanceOf[A])
    private var front = 0
    private var back = 0

    // In almost every data structure, removing is harder; stacks and queues are actually easier
    def dequeue(): A = {
        val ret = data(front)
        // Need to add one to index unless it needs to wrap
        // If index is 10 and size is 10, then % will give back 0 to wrap back
        front = (front + 1) % data.length
        ret
    }

    def enqueue(a: A): Unit = {
        // If full, then front and back will be same location and would indicate it's empty
        // So need to check next location in array
        if ((back+1) % data.length == front) {
            // Create a larger temporary array that's double the size
            // This is cost that is greater than 1; divided over all calls, it balances out back to O(1)
            // because frequency of doubling decreases exponentially
            val tmp = Array.fill(data.length * 2)(null.asInstanceOf[A])
            // Process to fill is take item at front, copy to index 0, loop around
            for (i <- 0 until data.length - 1) {
                tmp(i) = data((front + 1) % data.length)
            }
            front = 0
            back = data.length - 1
            data = tmp
        }
        data(back) = a
        back = (back + 1) % data.length
        
    }

    // Checks if front and back are equal
    def isEmpty: Boolean = front == back

    def peek: A = data(front)
}