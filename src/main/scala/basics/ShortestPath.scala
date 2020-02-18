package basics

import adt.ArrayQueue

object ShortestPath {
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

    // Create an array of offsets to give directions: left, right, up, down
    // Add four more elements to array to add diagonal directions
    val offsets = Array((-1, 0), (1, 0), (0, -1), (0, 1))
    
    // Recursion uses stack for breadth first search; depth first
    // Breadth does everything one step, then two steps, then three steps
    def breadthFirst(sx: Int, sy: Int, ex: Int, ey: Int): Int = {
        
        // Ints here, doubles for game as x, y coordinates; last Int is number of steps
        val queue = new ArrayQueue[(Int, Int, Int)]()
        val visited = collection.mutable.Set[(Int, Int)]((sx, sy))

        // sx is start x, sy is start y, 0 is starting point; 0 steps taken
        queue.enqueue((sx, sy, 0))
        // If queue still has stuff, then there are still potential locations
        while (!queue.isEmpty) {
            val (x, y, steps) = queue.dequeue()

            for ((offsetx, offsety) <- offsets) {
                val nx = x + offsetx
                val ny = y + offsety

                // nx, ny is new location; must exist as 0 or 1, ny can't exceed size of array; ny has to be less than the size of maze of x too; 
                // future location nx, ny must be equal to zero
                // Means inbounds and not in a wall
                // For graphic game, use maze.width and maze.height
                if (nx == ex && ny == ey) return steps + 1
                if (nx >= 0 && ny < maze.length && ny >= 0 && ny < maze(nx).length && maze(nx)(ny) == 0 && !visited((nx, ny))) {
                    queue.enqueue((nx, ny, steps + 1))
                    visited += nx -> ny
                }
            }
        }

        // Return really big number if path doesn't exist so min comparison works
        1000000000
    }

    def main(args: Array[String]): Unit = {
        println(breadthFirst(0, 0, 9, 9))
    }
}

