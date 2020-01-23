package mud

class Room(val name: String, val desc: String, private val exits: Array[Int], private var items: List[Item]) {


    def description(): String = ???

    def getExit(dir: Int) = ???

    def getItem(itemName: String) = {
        items.find(_.name == itemName.toLowerCase) match {
            case Some(item) => 
                // Filter will filter out all items that have same name and desc though because it's a case class
                // Better implementation of this process would be patch and getting index of list
                items = items.filter(_ != item)
                Some(item)
            case None => None
        }
    }

    def dropItem(item: Item) = items ::= item
}

object Room {
    val rooms = readRooms()

    def readRooms(): Array[Room] = {
        val source = scala.io.Source.fromFile("world.txt")
        val lines = source.getLines()
        val r = Array.fill(lines.next.toInt)(readRoom(lines))
        source.close()
        r
    }

    def readRoom(lines: Iterator[String]): Room = ???

}