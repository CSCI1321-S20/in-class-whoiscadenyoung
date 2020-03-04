package networking

import akka.actor.ActorSystem
import akka.actor.Props
import java.net.ServerSocket
import java.io.PrintStream
import java.io.InputStreamReader
import java.io.BufferedReader
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ActorChat extends App {
    // Start new Actor Application with System
    val system = ActorSystem("ActorChat")

    // Next, make actor
    val manager = system.actorOf(Props[ChatManager], "ChatManager")

    // Create scheduler to replace while loop
    // Scala concurrent has library for durations; converts milliseconds to seconds
    system.scheduler.schedule(1.seconds, 0.1.seconds, manager, ChatManager.CheckAllInput)

    // Next, create server functionality
    val ss = new ServerSocket(4040)

    // Allows unlimited connections
    while (true) {
        val socket = ss.accept()
        println("Testing the socks")

        // Make name, actor creation happen in a future because 
        Future {
            // To send a message across server, needs an output stream
            val os = socket.getOutputStream()
            // Wrap stream inside of printstream
            val out = new PrintStream(os)
            out.println("What is your name?")

            // Create input stream to read in text; need to convert input stream to reader first though
            val in = new BufferedReader(new InputStreamReader(socket.getInputStream()))

            // readLine is a blocking call, so it needs to happen in player so it doesn't block everyone
            // CheckInput message should be sent to player to check if there is input before reading it
            // Scheduler sends message to player ten times a second; blocking call causes this to blow up
            val name = in.readLine()

            // Use ready on ReaderStream to make sure input is ready to be read
            manager ! ChatManager.NewUser(name, socket, in, out)
        }
    }
}