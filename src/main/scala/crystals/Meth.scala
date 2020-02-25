package crystals

import akka.actor.Actor

class Meth extends Actor {
    // Import from companion object so you can use the case classes/objects
    import crystals.Meth.Move
    def receive = {
        case Move(x, y) =>
            // Will return for dx: -1, 0, or 1
            var dx = util.Random.nextInt(3) - 1
            var dy = util.Random.nextInt(2)

            while (dx == 0 && dy == 0) {
                dx = util.Random.nextInt(3) - 1
                dy = util.Random.nextInt(2)
            }

            // Sender is Manager, or can also use context.parent
            // Send message to parent to check if spot is open
            sender ! CrystalManager.TestMove(x + dx, y + dy)
        case m => println("Unhandled message in Meth: ", m)
    }
}

object Meth {
    // Put message types in companion object so it is organized in same file
    // Message should be case object or case class
    case class Move(x: Int, y: Int)
}