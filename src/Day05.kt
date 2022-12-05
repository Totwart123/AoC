import java.util.Stack

fun main() {
    fun part1(input: List<String>): String {
        val emptyLine = input.indexOfFirst{it.isEmpty()}

        val stacks = mutableMapOf<Int, ArrayDeque<String>>()

        val stackInfo = input.subList(0, emptyLine-1).reversed() //no need for the numbers
        val moveInfo = input.subList(emptyLine +1, input.size)

        stackInfo.forEach { line ->
            line.chunked(4).forEachIndexed { index, s ->
                if(s.isNotEmpty() && s.isNotBlank()){
                    stacks.getOrPut(index+1) { ArrayDeque() }.addFirst(s.trim().filterNot { it == '[' || it == ']' })
                }
            }
        }

        moveInfo.forEach { move ->
            val splittedMove = move.split(" ")
            val count = splittedMove[1].toInt()
            val from = splittedMove[3].toInt()
            val to = splittedMove[5].toInt()

            (1..count).forEach { _ ->
                if(stacks.containsKey(to)){
                    stacks[from]?.removeFirst()?.let { stacks[to]?.addFirst(it) }
                }
                else{
                    throw IllegalArgumentException("Der Stack existiert nicht.")
                }
            }
        }

        return stacks.map { it.value.first() }.toList().joinToString("")
    }

    fun part2(input: List<String>): String {
        val emptyLine = input.indexOfFirst{it.isEmpty()}

        val stacks = mutableMapOf<Int, MutableList<String>>()

        val stackInfo = input.subList(0, emptyLine-1).reversed() //no need for the numbers
        val moveInfo = input.subList(emptyLine +1, input.size)

        stackInfo.forEach { line ->
            line.chunked(4).forEachIndexed { index, s ->
                if(s.isNotEmpty() && s.isNotBlank()){
                    stacks.getOrPut(index+1) { mutableListOf() }.add(s.trim().filterNot { it == '[' || it == ']' })
                }
            }
        }

        moveInfo.forEach { move ->
            val splittedMove = move.split(" ")
            val count = splittedMove[1].toInt()
            val from = splittedMove[3].toInt()
            val to = splittedMove[5].toInt()

            if(stacks.containsKey(to) && stacks.containsKey(from)){
                stacks[from]?.takeLast(count)?.let { stacks[to]?.addAll(it) }
                stacks[from] = stacks[from]?.take((stacks[from]?.size ?: 0) - count)?.toMutableList()!!
            }
            else{
                throw IllegalArgumentException("Der Stack existiert nicht.")
            }

        }
        return stacks.map { if(it.value.isEmpty()) "" else it.value.last() }.toList().joinToString("")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

     val input = readInput("Day05")
     println(part1(input))
     println(part2(input))
}
