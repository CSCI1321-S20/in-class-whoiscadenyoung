package networking

import akka.actor.Actor
import java.io.BufferedReader
import java.net.Socket
import java.io.PrintStream

class User(name: String, socket: Socket, in: BufferedReader, out: PrintStream) extends Actor {
    // Each actor needs receiving method
    import User._
    def receive = {
        case CheckInput =>
            if (in.ready) {
                val input = in.readLine()
            }
        case m => println("Unhandles message in User: " + m)
    }
}

object User {
    case object CheckInput
}