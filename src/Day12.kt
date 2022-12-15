fun main() {
    data class Node(
        var visited:Boolean = false,
        val height:Int = -1,
        var distance: Int = Int.MAX_VALUE,
        val neighbors: MutableList<Node> = mutableListOf()
    )

    fun visitNext(nodeList: List<List<Node>>){
        val node = nodeList.flatten().filter { !it.visited && it.distance != Int.MAX_VALUE }.minByOrNull { it.distance }
        if(node != null){
            node.neighbors.filter { !it.visited && it.height <= node.height + 1 }.forEach {
                if(it.distance > node.distance + 1){
                    it.distance = node.distance + 1
                }
            }

            node.visited = true

            visitNext(nodeList)
        }
    }

    fun addNeigbours(nodes: List<List<Node>>){
        for(i in nodes.indices){
            for(j in nodes[i].indices){
                if(i > 0){
                    nodes[i][j].neighbors.add(nodes[i-1][j])
                }
                if(j < nodes[i].size - 1){
                    nodes[i][j].neighbors.add(nodes[i][j + 1])
                }
                if(i < nodes.size - 1){
                    nodes[i][j].neighbors.add(nodes[i+1][j])
                }
                if(j > 0){
                    nodes[i][j].neighbors.add(nodes[i][j-1])
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val nodes = input.map { line ->
            line.map {
                val height = if(it == 'S') 0 else if (it == 'E') 25 else it.code - 97
                Node(height = height)
            }
        }

        var start: Pair<Int, Int> = Pair(-1, -1)
        var end: Pair<Int, Int> = Pair(-1, -1)

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if(c == 'S'){
                    start = Pair(i,j)
                }

                if( c == 'E'){
                    end = Pair(i, j)
                }
            }
        }

        addNeigbours(nodes)

        nodes[start.first][start.second].distance = 0

        visitNext(nodes)

        return nodes[end.first][end.second].distance
    }

    fun part2(input: List<String>): Int {
        val nodes = input.map { line ->
            line.map {
                val height = if(it == 'S') 0 else if (it == 'E') 25 else it.code - 97
                Node(height = height)
            }
        }

        val possibleStarts:  MutableList<Pair<Int, Int>> = mutableListOf()
        var end: Pair<Int, Int> = Pair(-1, -1)

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if(c == 'S'){
                    possibleStarts.add(Pair(i,j))
                }

                if( c == 'E'){
                    end = Pair(i, j)
                }
                if(c == 'a'){
                    possibleStarts.add(Pair(i,j))
                }
            }
        }

        addNeigbours(nodes)

        var minSteps = Int.MAX_VALUE

        possibleStarts.forEach {start ->
            nodes.flatten().forEach {
                it.distance = Int.MAX_VALUE
                it.visited = false
            }

            nodes[start.first][start.second].distance = 0

            visitNext(nodes)

            val steps = nodes[end.first][end.second].distance

            minSteps = if(minSteps > steps) steps else minSteps
        }

        return minSteps
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
     println(part2(input))
}
