package crystals

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import akka.actor.ActorSystem
import akka.actor.Props

object Crystal extends JFXApp {
    val img = new WritableImage(800, 800)

    val system = ActorSystem("Crystals")

    // Because mutation is happening, there is a risk of race conditions already
    // Can create Parent Actor to ensure only one actor is changing at once
    val manager = system.actorOf(Props(new CrystalManager(img)))


    stage = new JFXApp.PrimaryStage {
        title = "Crystals!"
        scene = new Scene(800, 800) {
            val viewer = new ImageView(img)
            content = viewer
        }
    }
}