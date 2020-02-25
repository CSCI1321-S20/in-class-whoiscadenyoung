package actors

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

object ThreeActorCountdown {
    class CountingActor extends Actor {
        def receive = {
            case StartCounting(n, next, nextNext) =>
                println(n)
                next ! CountDown(n - 1, nextNext)
            case CountDown(n, next) => 
                println(n)
                if (n > 0) next ! CountDown(n - 1, sender)
                // Every actor has a context. Context includes actor system
                else context.system.terminate()
                // self allows to talk to self, sender allows to talk to sender
            // Always include m as last case in receive; will match everything
            case m => println("Unhandled message in Counting Actor: " + m)
        }
    }

    // Use case class to start off the counting; actor needs to know next actor and nextnext
    case class StartCounting(n: Int, next: ActorRef, nextNext: ActorRef)

    // Add case class or case object, depending on whether it takes arguments
    case class CountDown(n: Int, next: ActorRef)

    def main(args: Array[String]): Unit = {
        // Will keep going even after main stops in large application
        val system = ActorSystem("Counting")

        // Make an actor in your system. Pass in props (object that encapsulates how to build actors) 
        // because actors can only be created inside system
        // Props[CountingActor] specifies type to be created, will create it; or can just pass new actor in prop
        val actor1 = system.actorOf(Props[CountingActor], "Actor1")
        val actor2 = system.actorOf(Props[CountingActor], "Actor2")
        val actor3 = system.actorOf(Props[CountingActor], "Actor3")
        actor1 ! StartCounting(10, actor2, actor3)

        // If program terminates, message goe to Akka's "Dead Letter Office"
    }
}