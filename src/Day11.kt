import kotlin.math.round

fun main() {
    class Monkey(
        val number: Int,
        val items: MutableList<Long>,
        val operation: String,
        val operationValue: String,
        val test: Int,
        val testTrue: Int,
        val testFalse: Int,
        var itemsInspected: Long = 0
    ) {
        override fun toString(): String {
            return "Monkey(number=$number, items=$items, operation='$operation', operationValue=$operationValue, test=$test, testTrue=$testTrue, testFalse=$testFalse, itemsInspected=$itemsInspected)"
        }
    }

    fun getMonkey(monkyInfo: List<String>): Monkey {
        val monkey = monkyInfo[0].split(": ").last().split(" ").last().dropLast(1).toInt()

        val items = monkyInfo[1].split(": ").last().split(",").map { it.trim().toLong() }.toMutableList()

        val operation = monkyInfo[2].split(": ").last().split(" ")[3]
        check(listOf("-", "+", "*", "/").contains(operation)) { operation }

        val operationValue = monkyInfo[2].split(": ").last().split(" ")[4]

        check(monkyInfo[2].split(": ").last().split(" ")[2] == "old")
        check(operationValue.toIntOrNull() != null || operationValue == "old")

        check(monkyInfo[3].split(": ").last().split(" ").first() == "divisible")

        val test = monkyInfo[3].split(": ").last().split(" ").last().toInt()

        val testTrue = monkyInfo[4].split(": ").last().split(" ").last().toInt()
        val testFalse = monkyInfo[5].split(": ").last().split(" ").last().toInt()

        return Monkey(monkey, items, operation, operationValue, test, testTrue, testFalse)
    }

    fun calcMonkeys(input: List<String>, worryLevel: Int = 3, rounds: Int = 20): List<Monkey> {
        val monkeys = input.windowed(6, 7).map { getMonkey(it) }

        for (i in 0 until rounds){
            monkeys.forEach {monkey ->
                if (monkey.items.isNotEmpty()) {
                    monkey.items.forEach {itemValue ->
                        var item = itemValue

                        val operationValue = if (monkey.operationValue == "old") item else monkey.operationValue.toLong()

                        when(monkey.operation){
                            "-" -> item -= operationValue
                            "+" -> item += operationValue
                            "*" -> item *= operationValue
                            "/" -> item /= operationValue
                        }

                        item /= worryLevel

                        if(item % monkey.test == 0L){
                            monkeys.first { it.number == monkey.testTrue }.items.add(item)
                        }
                        else{
                            monkeys.first { it.number == monkey.testFalse }.items.add(item)
                        }

                    }

                    monkey.itemsInspected += monkey.items.size
                    monkey.items.clear()
                }
            }
        }

        return monkeys
    }

    fun part1(input: List<String>): Long {
        val monkeys = calcMonkeys(input, 3, 20)

        val sortedMonkeys = monkeys.sortedByDescending { it.itemsInspected };

        return sortedMonkeys[0].itemsInspected * sortedMonkeys[1].itemsInspected
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.windowed(6, 7).map { getMonkey(it) }

        var bigMod = 1

        monkeys.forEach { bigMod *= it.test }

        for (i in 0 until 10000){
            monkeys.forEach {monkey ->
                if (monkey.items.isNotEmpty()) {
                    monkey.items.forEach {itemValue ->
                        var item = itemValue

                        val operationValue = if (monkey.operationValue == "old") item else monkey.operationValue.toLong()

                        when(monkey.operation){
                            "-" -> item -= operationValue
                            "+" -> item += operationValue
                            "*" -> item *= operationValue
                            "/" -> item /= operationValue
                        }

                        item %= bigMod

                        if(item % monkey.test == 0L){
                            monkeys.first { it.number == monkey.testTrue }.items.add(item)
                        }
                        else{
                            monkeys.first { it.number == monkey.testFalse }.items.add(item)
                        }

                    }

                    monkey.itemsInspected += monkey.items.size
                    monkey.items.clear()
                }
            }
        }

        val sortedMonkeys = monkeys.sortedByDescending { it.itemsInspected }

        return sortedMonkeys[0].itemsInspected * sortedMonkeys[1].itemsInspected
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
     println(part2(input))
}
