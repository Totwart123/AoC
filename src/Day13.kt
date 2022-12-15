fun main() {
    fun getList(input: Iterator<Char>): List<Any> {
        val output = mutableListOf<Any>()
        var lastOneWasDiget = false

        while (input.hasNext()){
            val next = input.next()

            if(next == ']'){
                break
            }

            if(next == ','){
                lastOneWasDiget = false
                continue
            }

            if(next =='['){
                lastOneWasDiget = false
                output.add(getList(input))
            }
            else if(next.digitToIntOrNull() != null){
                if(lastOneWasDiget){
                    output[output.size - 1] = output[output.size - 1] as Int * 10 + next.digitToInt()
                }
                else{
                    output.add(next.digitToInt())
                }
                lastOneWasDiget = true
            }
        }

        return output
    }

    fun compareLists(left: Any?, right: Any?) : Int{
        if(left is Int && right is Int){
            return if(left < right){
                1
            } else if (left == right){
                0
            } else{
                -1
            }
        }

        if(left is List<*> && right is Int){
            return compareLists(left, listOf(right))
        }

        if(left is Int && right is List<*>){
            return compareLists(listOf(left), right)
        }

        if(left is List<*> && right is List<*>){
            for (i in 0 until left.size){
                if(right.size <= i){
                    return -1
                }

                val check = compareLists(left[i], right[i])

                if(check != 0){
                    return check
                }
            }

            return if(left.size == right.size) 0 else return 1
        }

        return 2
    }

    fun part1(input: List<String>): Int {
        var result = 0

        input.windowed(2, 3).forEachIndexed { index, pair ->
            val lists = pair.map { line ->
                getList(line.iterator()).first()
            }

            if(compareLists(lists.first(), lists.last()) == 1){
                result += index + 1
            }
        }

        return result
    }

    fun insertItem(result: MutableList<Any>, item: Any){
        var inserted = false

        if(result.isNotEmpty()){
            for(i in 0 until result.size){
                if(compareLists(item, result[i]) == 1){
                    result.add(i, item)
                    inserted = true
                    break
                }
            }
        }

        if(!inserted){
            result.add(item)
        }
    }

    fun part2(input: List<String>): Int {
        val result = mutableListOf<Any>()

        input.windowed(2, 3).forEachIndexed { index, pair ->
            val lists = pair.map { line ->
                getList(line.iterator()).first()
            }

            lists.forEach {
                insertItem(result, it)
            }
        }

        val divider1 = getList("[[2]]".iterator()).first()
        val divider2 = getList("[[6]]".iterator()).first()

        insertItem(result, divider1)
        insertItem(result, divider2)

        val index1 = result.indexOf(divider1) + 1
        val index2 = result.indexOf(divider2) + 1

        result.forEach { println(it) }
        return index1 * index2
    }

    val testInput = readInput("Day13_test")
    //check(part1(testInput) == 13)
    check(part2(testInput) == 140)

     val input = readInput("Day13")
     println(part1(input))
     println(part2(input))
}
