package adt

// First, write abstract supertype of queue
// Need type param for abstract data type; now is polymorphic
trait Queue[A] {
    // Use () to identify that it isn't just a value; it mutates the queue itself
    def enqueue(a: A): Unit
    def dequeue(): A

    // Checks first thing in line without taking it off
    def peek: A
    // Checks if the queue is empty
    def isEmpty: Boolean
}