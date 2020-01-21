package graphics

class Entity(val x: Double, val y: Double) {
    def width: Double = 1
    def height: Double = 1

    def intersects(other: Entity): Boolean = {
        (x - other.x).abs < (width + other.width) / 2 && (y - other.y).abs < (height + other.height) / 2
    }
}