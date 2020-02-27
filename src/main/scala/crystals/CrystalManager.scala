package crystals

import akka.actor.Actor
import scalafx.scene.image.WritableImage
import akka.actor.Props

class CrystalManager(img: WritableImage) extends Actor {
    for (_ <- 1 to 20) {
        // Context makes actor child of parent; context being class
        context.actorOf(Props[Meth])
    }
    for (child <- context.children) {
        child ! Meth.Move(400, 0)
    }

    import CrystalManager._
    def receive = {
        case TestMove(x, y, oldX, oldY) =>
            // x, y could be open space, out of bounds, or in a crystal that exists
            if (x < 0 || x >= img.width.value || y < 0 || y >= img.height.value) {
                sender ! Meth.Move(oldX, oldY)
            }
        case m => println("Unhandled message in CM: ", m)
    }
}

object CrystalManager {
    case class TestMove(x: Int, y: Int, oldX: Int, oldY: Int)
}