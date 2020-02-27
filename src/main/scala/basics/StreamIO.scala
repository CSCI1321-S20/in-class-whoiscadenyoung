package basics

import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.BufferedOutputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.BufferedInputStream
import java.io.FileInputStream

case class Student(name: String, id: String, gpa: Double)

object StreamIO extends App {
    // val students = List(
    //     Student("Name", "0123", 2.4),
    //     Student("Patrick", "0124", 3.78),
    //     Student("Dude 2", "0122", 0.12)
    // )
    // val fos = new FileOutputStream("students.bin")
    // val os = new ObjectOutputStream(new BufferedOutputStream(fos))
    // os.writeObject(students)
    // os.close()

    val is = new ObjectInputStream(new BufferedInputStream(new FileInputStream("students.bin")))
    val students = is.readObject()
    println(students)
    is.close()
}