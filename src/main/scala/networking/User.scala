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
        // in.ready is whenever they hit enter/submit; says there is something to read
        // telnet doesn't send anything until entire line is gotten; on Windows, telnet sends indiv chars
            if (in.ready) {
                val input = in.readLine()
                // message should be sent to everyone; can refer to sender or parent of all users (both are chat manager)
                sender ! ChatManager.SendToAll(name + ": " + input)
            }
        case PrintMessage(msg) =>
            out.println(msg)
        case m => println("Unhandles message in User: " + m)
    }
}

object User {
    case object CheckInput
    case class PrintMessage(msg: String)
}