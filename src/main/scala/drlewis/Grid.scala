package drlewis

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyCode

class Grid {

    // To prevent overlap, each cell is equal to true or false; higher odds equals more viruses
    val odds = 0.3

    // Making private var because current pill is modifiable; use method so renderer can't change it
    private var _currentPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor), new PillHalf(4, 0, ColorOption.randomColor)))
    private var _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor), new PillHalf(4, 0, ColorOption.randomColor)))
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

    private var keysHeld: Set[KeyCode] = Set()
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
            val movedPill = currentPill.move(0, 1, isClear)
            // Pill doesn't know if things are clear. Grid needs to tell the pill
            if (movedPill == _currentPill) {
                _elements +:= _currentPill
                _currentPill = _nextPill
                _nextPill = new Pill(Seq(new PillHalf(3, 0, ColorOption.randomColor), new PillHalf(4, 0, ColorOption.randomColor)))
            } else {
                _currentPill = movedPill
            }
            fallDelay = 0.0
        }
        if (moveDelay >= moveInterval) {
            if (keysHeld(KeyCode.Left)) _currentPill = currentPill.move(-1, 0, isClear)
            if (keysHeld(KeyCode.Right)) _currentPill = currentPill.move(1, 0, isClear)
            if (keysHeld(KeyCode.Up)) _currentPill = currentPill.rotate(isClear)
            if (keysHeld(KeyCode.Down)) _currentPill = currentPill.move(0, 1, isClear)
            moveDelay = 0.0
        }
    }

    def isClear(x: Int, y: Int): Boolean = {
        // If y is in bounds and x is in bounds and there is not an element at future location
        y < 16 && x >= 0 && x < 8 && !_elements.exists(e => e.cells.exists(c => c.x == x && c.y == y))
    }

    def keyPressed(keyCode: KeyCode): Unit = keysHeld += keyCode
    def keyReleased(keyCode: KeyCode): Unit = keysHeld -= keyCode
}