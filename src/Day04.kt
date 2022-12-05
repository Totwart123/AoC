fun main() {
    fun String.getRangeFromElf() : IntRange{
        val elf = this.split("-").map { it.toInt() }
        return IntRange(elf[0], elf[1])
    }

    fun part1(input: List<String>): Int {
        var doubleCount = 0
        input.forEach { pair->
            val splitted = pair.split(",").map { it.getRangeFromElf() }

            val firstElf = splitted[0]
            val secondElf = splitted[1]

            if(firstElf.contains(secondElf.first) && firstElf.contains(secondElf.last)||
                secondElf.contains(firstElf.first) && secondElf.contains(firstElf.last)){
                    doubleCount++
                }
        }
        return doubleCount
    }

    fun part2(input: List<String>): Int {
        var doubleCount = 0
        input.forEach { pair->
            val splitted = pair.split(",").map { it.getRangeFromElf() }

            val firstElf = splitted[0]
            val secondElf = splitted[1]

            if(firstElf.contains(secondElf.first) || firstElf.contains(secondElf.last)||
                secondElf.contains(firstElf.first) || secondElf.contains(firstElf.last)){
                doubleCount++
            }
        }
        return doubleCount
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 5)

     val input = readInput("Day04")
     println(part1(input))
     println(part2(input))
}
