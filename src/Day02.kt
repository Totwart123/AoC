fun main() {
    val duells = mutableListOf(
            0 to listOf(Pair('A', 'Z'), Pair('B', 'X'), Pair('C', 'Y')),
            3 to listOf(Pair('A', 'X'), Pair('B', 'Y'), Pair('C', 'Z')),
            6 to listOf(Pair('A', 'Y'), Pair('B', 'Z'), Pair('C', 'X')),
    )

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val move = it.trim()
            val enemyMove = move.first()
            val myMove = move.last()

            val movePoints = when(myMove){
                'X' -> 1
                'Y' -> 2
                'Z' -> 3
                else -> -1
            }
            check(movePoints != -1)

            val duellPoints = duells.first{ duell -> duell.second.contains(Pair(enemyMove, myMove)) }.first

            movePoints + duellPoints
        }
    }

    val predictedDuells = mutableListOf(
            1 to listOf(Pair('A', 'Y'), Pair('B', 'X'), Pair('C', 'Z')),
            2 to listOf(Pair('B', 'Y'), Pair('C', 'X'), Pair('A', 'Z')),
            3 to listOf(Pair('C', 'Y'), Pair('A', 'X'), Pair('B', 'Z')),
    )

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val move = it.trim()
            val enemyMove = move.first()
            val result = move.last()

            val movePoints = predictedDuells.first{ duell -> duell.second.contains(Pair(enemyMove, result)) }.first

            val duellPoints = when(result){
                'X' -> 0
                'Y' -> 3
                'Z' -> 6
                else -> -1
            }
            check(movePoints != -1)

            movePoints + duellPoints
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}
