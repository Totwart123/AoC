fun main() {
    fun getCalsByElves(input: List<String>): List<Int> {
        var numberInput = input.map { if (it.isEmpty()) -1 else it.toInt() }.toList()

        val emptyLines = numberInput.mapIndexed { index, i -> if (i == -1) index + 1 else -1 }.filter { it != -1 }.toMutableList().dropLast(0).reversed().toMutableList()
        emptyLines.add(0)

        val resultSet = emptyLines.map {
            val result = numberInput.takeLast(numberInput.size - it).sum()
            numberInput = numberInput.dropLast(numberInput.size - it).dropLast(1)
            result
        }.toList()

        return resultSet
    }

    fun part1(input: List<String>): Int {
        return getCalsByElves(input).max()
    }

    fun part2(input: List<String>): Int {
        return getCalsByElves(input).sortedDescending().take(3).sum()
    }

    check(part1(readInput("Day01_test")) == 24000)

    println(part1(readInput("Day01_test")))
    println(part1(readInput("Day01")))

    check(part2(readInput("Day01_test")) == 45000)

    println(part2(readInput("Day01_test")))
    println(part2(readInput("Day01")))
}
