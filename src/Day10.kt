fun main() {
    data class MutablePair<A, B>(var first: A, var second: B)

    fun part1(input: List<String>): Int {
        val iterator = input.iterator()

        var cycles = 0
        var register = 1

        val signalStrength = mutableMapOf<Int, Int>()

        var maxCycles = 0

        var currentExecution: MutablePair<Int, String>? = null

        while(cycles <= maxCycles){
            if(currentExecution == null && iterator.hasNext()){
                currentExecution = MutablePair(0, iterator.next().trim())

                if(currentExecution.second.startsWith("noop")){
                    maxCycles++
                }
                else{
                    maxCycles += 2
                }
            }

            cycles++
            println("$cycles: ${currentExecution?.second}")

            if(currentExecution != null){
                currentExecution.first++

                if(cycles % 40 == 20){
                    signalStrength[cycles] = cycles*register
                    println("$cycles - ${currentExecution?.first} - ${currentExecution?.second} - ${cycles*register}")
                }

                when(currentExecution.second.take(4)){
                    "noop" -> {
                        if(currentExecution.first == 1){
                            currentExecution = null
                        }
                    }
                    "addx" -> {
                        if(currentExecution.first == 2){
                            register += currentExecution.second.split(" ")[1].toInt()
                            currentExecution = null
                            println("register: $register")
                        }
                    }
                }
            }
        }

        return signalStrength.values.sum()
    }

    fun part2(input: List<String>): Int {
        val iterator = input.iterator()

        var cycles = 0
        var register = 1

        val signalStrength = mutableMapOf<Int, Int>()

        var maxCycles = 0

        var currentExecution: MutablePair<Int, String>? = null

        val rows = MutableList(6) { MutableList(40, ){"."}}

        while(cycles <= maxCycles){
            if(currentExecution == null && iterator.hasNext()){
                currentExecution = MutablePair(0, iterator.next().trim())

                assert(currentExecution != null)
                if(currentExecution!!.second.startsWith("noop")){
                    maxCycles++
                }
                else{
                    maxCycles += 2
                }
            }

            cycles++
            println("$cycles: ${currentExecution?.second}")

            if(currentExecution != null){
                currentExecution!!.first++

                val currentCrtPosition = (cycles - 1) % 40
                val currentRow = when(cycles){
                    in 1..40 -> 0
                    in 41..80 ->1
                    in 81..120 -> 2
                    in 121..160 -> 3
                    in 161..200 -> 4
                    else -> 5
                }

                if(currentCrtPosition == register || currentCrtPosition == register + 1 || currentCrtPosition == register - 1){
                    rows[currentRow][currentCrtPosition] = "#"
                    println(rows[currentRow].joinToString(""))
                }

                when(currentExecution!!.second.take(4)){
                    "noop" -> {
                        if(currentExecution!!.first == 1){
                            currentExecution = null
                        }
                    }
                    "addx" -> {
                        if(currentExecution!!.first == 2){
                            register += currentExecution!!.second.split(" ")[1].toInt()
                            currentExecution = null
                            println("register: $register")
                        }
                    }
                }
            }
        }

        rows.forEach {
            println(it.joinToString(""))
        }

        return 0
    }

    val testInput = readInput("Day10_test")
    //check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

     val input = readInput("Day10")
     //println(part1(input))
     println(part2(input))
}
