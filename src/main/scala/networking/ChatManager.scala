package networking

import akka.actor.Actor
import java.net.Socket
import java.io.BufferedReader
import java.io.PrintStream
import akka.actor.Props

class ChatManager extends Actor {
    import ChatManager._
    def receive = {
        case NewUser(name, socket, in, out) => 
            // System is not in here, so must use context
            // Second arg will not work if there are duplicates or if there are spaces or special characters b/c is name of Actor
            context.actorOf(Props(new User(name, socket, in, out)), name)
        case CheckAllInput =>
            for (child <- context.children) child ! User.CheckInput
        case m => println("Unhandles message in ChatManager: " + m)
    }
}

object ChatManager {
    case class NewUser(name: String, socket: Socket, in: BufferedReader, out: PrintStream)
    case object CheckAllInput
}