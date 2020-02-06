package drlewis

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Grid {

    // To prevent overlap, each cell is equal to true or false; higher odds equals more viruses
    val odds = 0.3

    // Making private var because current pill is modifiable; use method so renderer can't change it
    private var _currentPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor), new PillHalf(4, 0, ColorOption.randomColor)))
    def currentPill = _currentPill

    private var fallDelay = 0.0
    val fallInterval = 1.0

    private var moveDelay = 0.0
    val moveInterval = 0.1

    // Determines odds of a particular cell being true, hence being a virus
    // Old: List.fill(10)(new Virus(util.Random.nextInt(8), util.Random.nextInt(10) + 6, ColorOption.randomColor))
    private var _elements: Seq[Element] = for (i <- 0 until 8; j <- 6  until 16; if math.random < odds) yield {
        new Virus(i, j, ColorOption.randomColor)
    }

    private var leftHeld = false
    private var rightHeld = false
    private var upHeld = false
    
    // Elements will be redrawn each tick in the animation timer
    // +: is an operator that works on sequence
    def elements: Seq[Element] = currentPill +: _elements

    def update(delay: Double): Unit = {
        fallDelay += delay
        moveDelay += delay

        if (fallDelay >= fallInterval){
            // Pill doesn't know if things are clear. Grid needs to tell the pill
            _currentPill = currentPill.move(0, 1, isClear)
            fallDelay = 0.0
        }
        if (moveDelay >= moveInterval) {
            if (leftHeld) _currentPill = currentPill.move(-1, 0, isClear)
            if (rightHeld) _currentPill = currentPill.move(1, 0, isClear)
            if (upHeld) _currentPill = currentPill.rotate(isClear)
            moveDelay = 0.0
        }
    }

    def isClear(x: Int, y: Int): Boolean = {
        y < 16 && x >= 0 && x < 8
    }

    def leftPressed(): Unit = leftHeld = true
    def leftReleased(): Unit = leftHeld = false
    def rightPressed(): Unit = rightHeld = true
    def rightReleased(): Unit = rightHeld = false
    def upPressed(): Unit = upHeld = true
    def upReleased(): Unit = upHeld = false
}