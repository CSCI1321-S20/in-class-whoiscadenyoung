package actors

import akka.actor.Actor

object ThreeActorCountdown {
    class CountingActor extends Actor {
        def receive = {
            case m => println("Unhandled message in Counting Actor: " + m)
        }
    }
}