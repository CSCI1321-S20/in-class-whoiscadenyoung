package drlewis

import scalafx.scene.canvas.GraphicsContext

// If game is immutable, will delete and create new object from existing every time it moves
// Works better in parallel
trait GridCell {
    // Def are methods, so they get reevaluated; if immutable, can be vals
    def x: Int
    def y: Int
    def color: ColorOption.Value
}