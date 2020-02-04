package drlewis

class Virus(val x: Int, val y: Int, val color: ColorOption.Value) extends GridCell with Element {
    // Cell should be a list of itself
    def cells: Seq[drlewis.GridCell] = Seq(this)
}