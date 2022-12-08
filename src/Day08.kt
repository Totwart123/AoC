import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        var treeCount = input.size * 2 + 2 * (input.first().length - 2)

        (1 until input.size - 1).forEach { i ->
            (1 until input[i].length - 1).forEach { j ->
                val currentTree = input[i][j].toString().toInt()
                //check left
                if (input[i].take(j).map { it.toString().toInt() }.max() < currentTree)
                    treeCount++
                //check right
                else if(input[i].takeLast(input[i].length - j - 1).map { it.toString().toInt() }.max() < currentTree)
                    treeCount++
                //check top
                else if(input.take(i).maxOf { it[j].toString().toInt() } < currentTree)
                    treeCount ++
                else if(input.takeLast(input.size - i - 1).maxOf { it[j].toString().toInt() } < currentTree)
                    treeCount ++
            }
        }

        return treeCount
    }

    fun calcScienceMulti(treesToCheck: List<Int>, currentTree: Int): Int{
        val visibleTrees = treesToCheck.takeWhile { it < currentTree }

        val scienceMultiplicator = if (visibleTrees.size == treesToCheck.size){
            visibleTrees.size
        } else{
            visibleTrees.size + 1
        }
        return scienceMultiplicator
    }

    fun part2(input: List<String>): Int {
        var highestScienceScore = -1

        (input.indices).forEach { i ->
            (0 until input[i].length).forEach { j ->
                var scienceScore = 1

                val currentTree = input[i][j].toString().toInt()
                //left
                val treesLeft = input[i].take(j).map { it.toString().toInt() }.reversed()
                scienceScore *= calcScienceMulti(treesLeft, currentTree)

                val treesRight = input[i].takeLast(input[i].length - j - 1).map { it.toString().toInt() }
                scienceScore *= calcScienceMulti(treesRight, currentTree)

                val treesTop = input.take(i).map { it[j].toString().toInt() }.reversed()
                scienceScore *= calcScienceMulti(treesTop, currentTree)

                val treesBottom = input.takeLast(input.size - i - 1).map { it[j].toString().toInt() }
                scienceScore *= calcScienceMulti(treesBottom, currentTree)

                if(scienceScore > highestScienceScore){
                    highestScienceScore = scienceScore
                }
            }
        }

        return highestScienceScore
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

     val input = readInput("Day08")
     println(part1(input))
     println(part2(input))
}
