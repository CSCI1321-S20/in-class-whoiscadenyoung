package drlewis

trait Element {
    // Using Sequence as common supertype for anything linearly indexed (List, Array, )
    def cells: Seq[GridCell]
}