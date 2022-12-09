import kotlin.math.absoluteValue

fun main() {
    data class MutablePair(var first: Int, var second: Int){
        override fun equals(other: Any?): Boolean {
            if(other?.javaClass !=  javaClass) return false

            other as MutablePair

            return other.first == first && other.second == second
        }
    }

    fun printMoves(head: MutablePair, tails: List<MutablePair>){
        val temp = tails.toMutableList()
        temp.add(head)

        val maxX = temp.maxOf { it.first }
        val maxY = temp.maxOf { it.second }

        val minX = temp.minOf { it.first }
        val minY = temp.minOf { it.second }

        for(i in maxY downTo minY){
            var line = ""
            for(j in minX..maxX){
                val tmp = MutablePair(j, i)
                val indexOf = tails.indexOf(tmp)
                if(head == tmp){
                    line += "H"
                }
                else if(indexOf != - 1){
                    line += "${indexOf + 1}"
                }
                else{
                    line += "."
                }
            }
            println(line)
        }

    }

    fun tailIsArroundHead(head: MutablePair, tail: MutablePair): Boolean{
        if(head == tail) return true
        if(head.first == tail.first && (head.second == tail.second + 1 || head.second == tail.second - 1)) return true
        if(head.second == tail.second && (head.first == tail.first + 1 || head.first == tail.first - 1)) return true
        //Top left
        if(head.first - 1 == tail.first && head.second + 1 == tail.second) return true
        //Top right
        if(head.first + 1 == tail.first && head.second + 1 == tail.second) return true
        //bottom left
        if(head.first - 1 == tail.first && head.second - 1 == tail.second) return true
        //bottom right
        if(head.first + 1 == tail.first && head.second - 1 == tail.second) return true

        return false
    }

    fun part1(input: List<String>): Int {
        val tail = MutablePair(0,0)
        val head = MutablePair(0,0)

        val positions = mutableListOf(Pair(tail.first, tail.second))

        input.forEach { move ->
            val splitted = move.split(" ")
            val direction = splitted[0]
            val count = splitted[1].toInt()

            for (i in 0 until count ){
                when(direction){
                    "U" -> head.second++
                    "D" -> head.second--
                    "R" -> head.first++
                    "L" -> head.first--
                }

                if(!tailIsArroundHead(head, tail)){
                    val xDif = (head.first - tail.first).absoluteValue
                    val yDif = (head.second - tail.second).absoluteValue

                    if(xDif == 0 || xDif < yDif){
                        if(head.second > tail.second){
                            tail.second = head.second - 1
                        }
                        else{
                            tail.second = head.second + 1
                        }

                        tail.first = head.first
                    }
                    else if(yDif == 0 || xDif > yDif){
                        if(head.first > tail.first){
                            tail.first = head.first - 1
                        }
                        else{
                            tail.first = head.first + 1
                        }

                        tail.second = head.second
                    }

                    val positionPair = Pair(tail.first, tail.second)

                    if(!positions.contains(positionPair)){
                        positions.add(positionPair)
                    }
                }
            }
        }

        return positions.count()
    }

    fun part2(input: List<String>): Int {
        val tails = (0..8).map { MutablePair(0,0) }.toMutableList()
        val head = MutablePair(0,0)

        val positions = mutableListOf(Pair(tails.last().first, tails.last().second))

        input.forEach { move ->
            val splitted = move.split(" ")
            val direction = splitted[0]
            val count = splitted[1].toInt()

             for (i in 0 until count ){
                when(direction){
                    "U" -> head.second++
                    "D" -> head.second--
                    "R" -> head.first++
                    "L" -> head.first--
                }

                 println("####HEAD-MOVES####")

                 printMoves(head, tails)

                var tailInFront = head

                for(tail in tails){
                    if(!tailIsArroundHead(tailInFront, tail)){
                        val xDif = (tailInFront.first - tail.first).absoluteValue
                        val yDif = (tailInFront.second - tail.second).absoluteValue

                        if(xDif == 0 || xDif < yDif){
                            if(tailInFront.second > tail.second){
                                tail.second = tailInFront.second - 1
                            }
                            else{
                                tail.second = tailInFront.second + 1
                            }

                            tail.first = tailInFront.first
                        }
                        else if(yDif == 0 || xDif > yDif){
                            if(tailInFront.first > tail.first){
                                tail.first = tailInFront.first - 1
                            }
                            else{
                                tail.first = tailInFront.first + 1
                            }

                            tail.second = tailInFront.second
                        }
                        else if(xDif > 1){
                            if(tailInFront.second > tail.second){
                                tail.second = tailInFront.second - 1
                            }
                            else{
                                tail.second = tailInFront.second + 1
                            }

                            if(tailInFront.first > tail.first){
                                tail.first = tailInFront.first - 1
                            }
                            else{
                                tail.first = tailInFront.first + 1
                            }
                        }
                    }
                    tailInFront = tail

                    printMoves(head, tails)
                }

                val positionPair = Pair(tails.last().first, tails.last().second)

                if(!positions.contains(positionPair)){
                    positions.add(positionPair)
                }
            }
        }
        return positions.count()
    }

    var testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    testInput = readInput("Day09_test2")
    check(part2(testInput) == 36)

     val input = readInput("Day09")
     println(part1(input))
     println(part2(input))
}
