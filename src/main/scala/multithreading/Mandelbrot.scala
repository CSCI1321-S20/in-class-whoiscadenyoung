package multithreading

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import scalafx.scene.image.PixelWriter
import basics.Complex
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseButton
import scalafx.scene.input.KeyCode

object Mandelbrot extends JFXApp {
  private var maxCount = 10000
  private var realMin = -2.0
  private var realMax = 1.0
  private var imaginaryMin = -1.5
  private var imaginaryMax = 1.5

  stage = new JFXApp.PrimaryStage {
    title = "Mandelbrot"
    scene = new Scene(1000, 1000) {
      val wimg = new WritableImage(1000, 1000)
      val view = new ImageView(wimg)
      content = view
      calcMandelbrot(wimg)

      onMouseClicked = me => {
        val x = realMin + me.x * (realMax - realMin) / wimg.width.value
        val y = imaginaryMin + me.y * (imaginaryMax - imaginaryMin) / wimg.height.value
        if (me.button == MouseButton.PRIMARY) {
          println(s"Julia at $x+${y}i")
          new Julia(Complex(x, y))
        } else {
          val realDelta = (realMax - realMin) * 0.05
          val imaginaryDelta = (imaginaryMax - imaginaryMin) * 0.05
          realMin = x - realDelta
          realMax = x + realDelta
          imaginaryMin = y - imaginaryDelta
          imaginaryMax = y + imaginaryDelta
          maxCount *= 2
          println(s"zoom at $x+${y}i")
          calcMandelbrot(wimg)
        }
      }

      onKeyPressed = ke => {
        if (ke.code == KeyCode.Space) {
          realMin = -2.0
          realMax = 1.0
          imaginaryMin = -1.5
          imaginaryMax = 1.5
          maxCount = 10000
          calcMandelbrot(wimg)
        }
      }
    }
  }

  def calcMandelbrot(wimg: WritableImage): Unit = {
    val writer = wimg.pixelWriter
    val start = System.nanoTime()

    // Change first collection to .par to increase load time drastically; 
    // On lab computer went from 10 seconds to 8
    for (i <- (0 until wimg.width.value.toInt).par; 
        val x = realMin + i * (realMax - realMin) / wimg.width.value; 
        j <- 0 until wimg.height.value.toInt) {
      val y = imaginaryMin + j * (imaginaryMax - imaginaryMin) / wimg.height.value
      val cnt = mandelCount(Complex(x, y))
      val color = if (cnt >= maxCount) Color.Black else Color(math.log(cnt + 1) / math.log(maxCount + 1), 0.0, 0.0, 1.0)
      writer.setColor(i, j, color)
    }
    println("Time taken: " + (System.nanoTime() - start)*1e-9)
  }

  def mandelCount(c: Complex): Int = {
    var cnt = 0
    var z = Complex(0, 0)
    while (cnt < maxCount && z.magSqr < 9) {
      cnt += 1
      z = z * z + c
    }
    cnt
  }
}