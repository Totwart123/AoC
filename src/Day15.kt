import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {
    data class Beacon(val x: Int, val y: Int){
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Beacon

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    data class Sensor(val x: Int, val y: Int, val nearestBeacon: Beacon, val distanceToBeacon: Int)

    fun calcDistance(from: Pair<Int,Int>, to: Pair<Int, Int>): Int{
        return (from.first - to.first).absoluteValue + (from.second - to.second).absoluteValue
    }

    fun calcPossibleBeaconPositionsInRow(sensors: List<Sensor>, beacons: List<Beacon>, row: Int) : Int{
        val maxDistance = sensors.maxOf { it.distanceToBeacon }
        val minX = sensors.minOf { it.x } - maxDistance
        val maxX = sensors.maxOf { it.x } + maxDistance

        var countBeaconNotToBe = 0

        for(i in minX..maxX){

            if(sensors.any{calcDistance(Pair(it.x, it.y), Pair(i, row)) <= it.distanceToBeacon} && !beacons.any { it.x == i && it.y == row }){
                countBeaconNotToBe ++
            }
        }

        return countBeaconNotToBe
    }

    fun calcPossibleBeaconPositionInRowSlow(sensors: List<Sensor>, beacons: List<Beacon>, max: Int) : Pair<Int, Int>{
        val maxDistance = sensors.maxOf { it.distanceToBeacon }
        var minX = sensors.minOf { it.x } - maxDistance
        var maxX = sensors.maxOf { it.x } + maxDistance

        var minY = sensors.minOf { it.y } - maxDistance
        var maxY = sensors.maxOf { it.y } + maxDistance

        minX = if(minX < 0) 0 else minX
        minY = if(minY < 0) 0 else minY

        maxX = if(maxX > max) max else maxX
        maxY = if(maxY > max) max else maxY

        for(i in minY until maxY){
            for(j in minX until maxX){
                if(!sensors.any{calcDistance(Pair(it.x, it.y), Pair(j, i)) <= it.distanceToBeacon} && !beacons.any { it.x == j && it.y == i }){
                    return Pair(j, i)
                }
            }
        }


        return Pair(-1, -1)
    }

    fun calcRange(sensor: Sensor, row: Int): Pair<Int, Int>? {
        val yDist = abs(sensor.y - row)
        if(yDist > sensor.distanceToBeacon){
            return null
        }

        val xDist = sensor.distanceToBeacon - yDist

        return Pair(sensor.x - xDist, sensor.x + xDist)
    }

    fun part2Fast(sensors: List<Sensor>, max: Int, beacons: List<Beacon>) : Pair<Int, Int>{
        for(row in 0 until max){
            println(row)
            val ranges = sensors.mapNotNull {
                calcRange(it, row)
            }.toMutableList()

            val newRanges = ranges.toMutableList()

            var isOverlapping = true

            while(isOverlapping){
                isOverlapping = false

                for(i in ranges.indices){
                    for(j in i+1 until ranges.size){
                        val a = ranges[i].first
                        val b = ranges[i].second
                        val c = ranges[j].first
                        val d = ranges[j].second

                        if(a <= d && b >= c){
                            val left = min(a, c)
                            val right = Math.max(b, d)

                            newRanges.remove(ranges[i])
                            newRanges.remove(ranges[j])

                            newRanges.add(Pair(left, right))

                            isOverlapping = true
                        }
                    }
                }

                ranges.clear()
                ranges.addAll(newRanges)
            }

            if(ranges.size > 1){
                val x = if (ranges[0].first < ranges[1].first) ranges[0].second + 1 else ranges[1].second + 1

                if(!beacons.any{it.x == x && it.y == row}){
                    return Pair(x, row)
                }
            }
        }

        return Pair(-1 , -1)
    }

    fun part1(input: List<String>, row: Int): Int {
        val beacons = mutableListOf<Beacon>()
        val sensors = mutableListOf<Sensor>()
        input.forEach { line ->
            val sensorData = line.split(":")[0].replace("Sensor at ", "")
            val beaconData = line.split(":")[1].replace(" closest beacon is at ", "")

            var coords = beaconData.split(",")
            val beacon = Beacon(coords[0].split("=")[1].toInt(), coords[1].split("=")[1].toInt())

            if(!beacons.contains(beacon)){
                beacons.add(beacon)
            }

            coords = sensorData.split(",")

            val sensorX = coords[0].split("=")[1].toInt()
            val sensorY =  coords[1].split("=")[1].toInt()

            val distance = calcDistance(Pair(beacon.x, beacon.y), Pair(sensorX, sensorY))

            val sensor = Sensor(sensorX, sensorY, beacon, distance)

            sensors.add(sensor)
        }

        return calcPossibleBeaconPositionsInRow(sensors, beacons, row)
    }

    fun part2(input: List<String>, max: Int): Int {
        val beacons = mutableListOf<Beacon>()
        val sensors = mutableListOf<Sensor>()
        input.forEach { line ->
            val sensorData = line.split(":")[0].replace("Sensor at ", "")
            val beaconData = line.split(":")[1].replace(" closest beacon is at ", "")

            var coords = beaconData.split(",")
            val beacon = Beacon(coords[0].split("=")[1].toInt(), coords[1].split("=")[1].toInt())

            if(!beacons.contains(beacon)){
                beacons.add(beacon)
            }

            coords = sensorData.split(",")

            val sensorX = coords[0].split("=")[1].toInt()
            val sensorY =  coords[1].split("=")[1].toInt()

            val distance = calcDistance(Pair(beacon.x, beacon.y), Pair(sensorX, sensorY))

            val sensor = Sensor(sensorX, sensorY, beacon, distance)

            sensors.add(sensor)
        }

        val result = part2Fast(sensors, max, beacons)

        return result.first * 4000000 + result.second
    }

    val testInput = readInput("Day15_test")
    //check(part1(testInput, 10) == 26)
    check(part2(testInput, 20) == 56000011)

     val input = readInput("Day15")
     //println(part1(input, 2000000))
     println(part2(input, 4000000))
}
