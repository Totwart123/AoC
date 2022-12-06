fun main() {
    fun part1(input: List<String>, window: Int): Int {
        val line = input.first()
        var result = -1

        run breaking@ {
            line.windowed(window, 1).forEachIndexed { index, s ->
                if(s.toCharArray().distinct().size == s.length){
                    result = index + s.length
                    return@breaking
                }
            }
        }
        return result
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput, 4) == 7)
    check(part1(testInput, 14) == 19)

     val input = readInput("Day06")
     println(part1(input,4))
     println(part1(input, 14))
}
