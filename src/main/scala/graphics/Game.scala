package graphics

object Game extends App {
    val e1 = new Entity(0, 0)
    val e2 = new Entity(3, 3)
    val e3 = new Entity(0.5, 0.5)

    println(e1.intersects(e2))
    println(e1.intersects(e3))
    println("Howdy")
}
