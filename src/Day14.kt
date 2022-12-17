enum class Materials{
    AIR, ROCK, SAND
}

fun main() {
    fun printGround(ground: List<List<Materials>>){
        var xOffset = Int.MAX_VALUE
        ground.forEach { line ->
            line.forEachIndexed { index, materials ->
                if(index > xOffset){
                    return@forEachIndexed
                }

                if(materials != Materials.AIR){
                    xOffset = index
                }
            }
        }

        ground.map { line ->
            line.map{
                when(it){
                    Materials.AIR -> "."
                    Materials.ROCK -> "#"
                    Materials.SAND -> "o"
                }
            }
        }.forEach {
            println(it.takeLast(it.size-xOffset).joinToString(""))
        }
    }

    fun drawLines(ground: MutableList<MutableList<Materials>>, lines: List<Pair<Int, Int>>){
        for(i in 0 until lines.size - 1){
            val from = lines[i]
            val to = lines[i+1]

            if(from.first == to.first){
                val fromy = if(from.second > to.second)to.second else from.second

                val toy = if(from.second < to.second)to.second else from.second

                for(j in fromy..toy){
                    ground[j][from.first] = Materials.ROCK
                }
            }

            if(from.second == to.second){
                val fromx = if(from.first > to.first)to.first else from.first

                val tox = if(from.first < to.first)to.first else from.first

                for(j in fromx..tox){
                    ground[from.second][j] = Materials.ROCK
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val coords = input.map {line ->
            line.split("->").map { it.trim() }.map {
                val coords = it.split(",")
                val x = coords[0].toInt()
                val y = coords[1].toInt()
                Pair(x, y)
            }
        }

        val xOffset = coords.flatten().minOf { it.first }

        val ground = mutableListOf<MutableList<Materials>>()

        for(i in 0 until coords.flatten().maxOf { it.second } + 3){
            ground.add(mutableListOf())
            for(j in 0 until coords.flatten().maxOf { it.first } + 1){
                ground[i].add(Materials.AIR)
            }
        }

        coords.forEach { drawLines(ground, it) }

        var currentSandTile = Pair(500,0)
        var currentSandTileCount = 1
        val maxY = coords.flatten().maxOf { it.second }
        var oldSandTile: Pair<Int, Int>? = null

        while(oldSandTile != currentSandTile){
            oldSandTile = currentSandTile

            if(ground.first().size == currentSandTile.first + 1){
                ground.forEach { line ->
                    line.add(Materials.AIR)
                }
            }

            when (Materials.AIR) {
                ground[currentSandTile.second + 1][currentSandTile.first] -> {
                    currentSandTile = Pair(currentSandTile.first, currentSandTile.second + 1)
                }
                ground[currentSandTile.second + 1][currentSandTile.first - 1] -> {
                    currentSandTile = Pair(currentSandTile.first - 1, currentSandTile.second + 1)
                }
                ground[currentSandTile.second + 1][currentSandTile.first + 1] -> {
                    currentSandTile = Pair(currentSandTile.first + 1, currentSandTile.second + 1)
                }
                else -> {
                    if(currentSandTile == Pair(500, 0)) break;

                    currentSandTile = Pair(500, 0)
                    oldSandTile = null
                    currentSandTileCount ++
                    //printGround(ground)
                }
            }

            if(currentSandTile.second == maxY + 2){
                currentSandTile = Pair(500, 0)
                oldSandTile = null
                currentSandTileCount ++
                //printGround(ground)
            }

            oldSandTile?.let { ground[it.second][it.first] = Materials.AIR }

            ground[currentSandTile.second][currentSandTile.first] = Materials.SAND
        }



        return currentSandTileCount
    }



    fun part1(input: List<String>): Int {
        val coords = input.map {line ->
            line.split("->").map { it.trim() }.map {
                val coords = it.split(",")
                val x = coords[0].toInt()
                val y = coords[1].toInt()
                Pair(x, y)
            }
        }

        val xOffset = coords.flatten().minOf { it.first }

        val ground = mutableListOf<MutableList<Materials>>()

        for(i in 0 until coords.flatten().maxOf { it.second } + 1){
            ground.add(mutableListOf())
            for(j in 0 until coords.flatten().maxOf { it.first } + 1){
                ground[i].add(Materials.AIR)
            }
        }

        coords.forEach { drawLines(ground, it) }

        var currentSandTile = Pair(500,0)
        var currentSandTileCount = 1
        val maxY = coords.flatten().maxOf { it.second }

        while(currentSandTile.second < maxY){
            var oldSandTile = currentSandTile
            when (Materials.AIR) {
                ground[currentSandTile.second + 1][currentSandTile.first] -> {
                    currentSandTile = Pair(currentSandTile.first, currentSandTile.second + 1)
                }
                ground[currentSandTile.second + 1][currentSandTile.first - 1] -> {
                    currentSandTile = Pair(currentSandTile.first - 1, currentSandTile.second + 1)
                }
                ground[currentSandTile.second + 1][currentSandTile.first + 1] -> {
                    currentSandTile = Pair(currentSandTile.first + 1, currentSandTile.second + 1)
                }
                else -> {
                    currentSandTile = Pair(500, 0)
                    oldSandTile = currentSandTile
                    currentSandTileCount ++
                    printGround(ground)
                }
            }

            ground[oldSandTile.second][oldSandTile.first] = Materials.AIR
            ground[currentSandTile.second][currentSandTile.first] = Materials.SAND
        }



        return currentSandTileCount-1
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

     val input = readInput("Day14")
     println(part1(input))
     println(part2(input))
}
