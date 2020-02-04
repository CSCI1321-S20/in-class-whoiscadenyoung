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
}