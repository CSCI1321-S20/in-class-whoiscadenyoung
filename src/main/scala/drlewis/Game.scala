package drlewis

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

/* 
Grid
Grid Cell: Pill Half -- (Pill, Virus) <- Element
*/

object Game extends JFXApp {
    val grid = new Grid
    // Must define canvas, graphics context outside of scene so renderer can draw to it
    val canvas = new Canvas(800, 800)
    val gc = canvas.graphicsContext2D
    val renderer = new Renderer(gc)

    stage = new JFXApp.PrimaryStage {
        title = "Dr. Lewis"
        scene = new Scene(800, 800) {
            content = canvas

            onKeyPressed = (ke: KeyEvent) => {
                ke.code match {
                    // If doing move on each key update, subject to keyboard repeat delay of user
                    // Instead, just pass in that a key is pressed or released
                    case KeyCode.Left => grid.leftPressed()
                    case KeyCode.Right => grid.rightPressed()
                    case KeyCode.Up => grid.upPressed()
                    case _ => 
                }
            }

            onKeyReleased = (ke: KeyEvent) => {
                ke.code match {
                    case KeyCode.Left => grid.leftReleased()
                    case KeyCode.Right => grid.rightReleased()
                    case KeyCode.Up => grid.upReleased()
                    case _ => 
                }
            }

            // By default, counts up in nanoseconds
            // Use lastTime to hold a delay
            // All movement should be a speed * delay; Dr. mario is not smooth motion, but game should be
            var lastTime = -1L
            val timer = AnimationTimer(time => {
                if (lastTime >= 0) {
                    val delay = (time - lastTime) / 1e9

                    // Do not use this code for game
                    grid.update(delay)
                    renderer.render(grid)
                }
                lastTime = time
            })
            timer.start()
        }
    }
}