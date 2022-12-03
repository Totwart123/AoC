fun main() {
    fun Char.getValueOfItem() = if(this.isUpperCase()) this.code - 38 else this.code - 96

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {items ->
            val compartment1 = items.substring(0, items.length / 2)
            val compartment2 = items.substring(items.length/2, items.length)

            val double = compartment1.first { compartment2.contains(it) }

            sum += double.getValueOfItem()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.chunked(3).forEach { group ->
            val itemType =  group[0].first { group[1].contains(it) && group[2].contains(it) }

            sum += itemType.getValueOfItem()
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

     val input = readInput("Day03")
     println(part1(input))
     println(part2(input))

}
