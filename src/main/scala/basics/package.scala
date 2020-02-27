package object Path {
  private var startX, startY = 0
  private var endX, endY = 9
  private val offsets = Vector(0 -> -1, 1 -> 0, 0 -> 1, -1 -> 0)

  private var shortPath: List[(Int, Int)] = Nil

breadth().foreach{ lst =>
    shortPath = lst
  }

  // if shortPath contains the cell, can color it or something;

  val maze = Array(
    Array(0,1,0,0,0,0,0,0,0,0),
    Array(0,1,0,1,1,1,1,1,1,0),
    Array(0,1,0,0,0,1,0,0,0,0),
    Array(0,1,1,1,0,1,0,1,1,1),
    Array(0,1,0,0,0,1,0,0,0,0),
    Array(0,0,0,0,0,1,0,1,1,0),
    Array(0,1,1,1,1,1,0,0,0,0),
    Array(0,1,0,0,0,1,1,0,1,0),
    Array(0,1,0,0,0,1,0,0,1,0),
    Array(0,0,0,0,0,0,0,0,1,0)
  )
  def breadth(): Option[List[(Int, Int)]] = {
    val queue = new adt.ArrayQueue[List[(Int, Int)]]()
    // Queue in start location
    queue.enqueue(List(startX -> startY))

    // the solution; defaults to None
    var solution: Option[List[(Int, Int)]] = None
    // Checks where you've been
    var visited = Set[(Int, Int)](startX -> startY)

    // make sure stuff on queue and there is no solution existing
    while (!queue.isEmpty && solution.isEmpty) {
      // Pull off the head of the list
      // @ bounds name to entire pattern; whole list is steps; x, y is pulled off
      val steps @ ((x, y) :: _) = queue.dequeue()

      // will try all different move possibilities
      for ((dx, dy) <- offsets) {
        val nx = x + dx
        val ny = y + dy

        // Check if movement is valid; check if solution; if not, add possible to queue
        // Process of pulling first item, adding any other possible items
        // Check if: Not negative index, index less than height, not negative index, index less than width,
        // and position does not move into a wall
        if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze(nx).length && maze(nx)(ny) == 0 && !visited(nx -> ny)) {
          // Condition checks if solution has been found
          if (nx == endX && ny == endY) {
            solution = Some((nx -> ny) :: steps)
          } else {
            visited += nx -> ny
            queue.enqueue((nx -> ny) :: steps)
          }
        }

      }
    }
    solution
  }

}
