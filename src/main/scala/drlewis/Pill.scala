package drlewis

class Pill(halves: Seq[PillHalf]) extends Element {
    // Pill Half is a grid cell, so can pass in pill half or virus to anything that takes gridcell
    def cells: Seq[GridCell] = halves

    // Should be double in game for better collision check
    // Pass in isClear function with x, y to see if thing is allowed to move there
    def move(dx: Int, dy: Int, isClear: (Int, Int) => Boolean): Pill = {
        // Only moves if both pill halves are allowed to move; else returns current pill
        if (halves.forall(_.moveAllowed(dx, dy, isClear)))
            new Pill(halves.map(_.move(dx, dy)))
        else this
    }

    def rotate(isClear: (Int, Int) => Boolean): Pill = {
        if (halves.length < 2) this
        // Check if they're vertical
        else {
            val newPill = if (halves(0).y == halves(1).y) {
                // Will sort to find one on the left
                val s = halves.sortBy(_.x)
                new Pill(Seq(s(0).move(1, 0), s(1).move(0, -1)))
            } else {
                val s = halves.sortBy(_.y)
                new Pill(Seq(s(0).move(-1, 1), s(1)))
            }
            if (newPill.halves.forall(ph => isClear(ph.x, ph.y))) newPill else this
    }
}