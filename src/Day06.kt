import java.math.BigInteger

val ZERO = 0.toBigInteger()

fun main() {
    val input = listOf("1,3,4,1,5,2,1,1,1,1,5,1,5,1,1,1,1,3,1,1,1,1,1,1,1,2,1,5,1,1,1,1,1,4,4,1,1,4,1,1,2,3,1,5,1,4,1,2,4,1,1,1,1,1,1,1,1,2,5,3,3,5,1,1,1,1,4,1,1,3,1,1,1,2,3,4,1,1,5,1,1,1,1,1,2,1,3,1,3,1,2,5,1,1,1,1,5,1,5,5,1,1,1,1,3,4,4,4,1,5,1,1,4,4,1,1,1,1,3,1,1,1,1,1,1,3,2,1,4,1,1,4,1,5,5,1,2,2,1,5,4,2,1,1,5,1,5,1,3,1,1,1,1,1,4,1,2,1,1,5,1,1,4,1,4,5,3,5,5,1,2,1,1,1,1,1,3,5,1,2,1,2,1,3,1,1,1,1,1,4,5,4,1,3,3,1,1,1,1,1,1,1,1,1,5,1,1,1,5,1,1,4,1,5,2,4,1,1,1,2,1,1,4,4,1,2,1,1,1,1,5,3,1,1,1,1,4,1,4,1,1,1,1,1,1,3,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,5,1,2,1,1,1,1,1,1,1,1,1")

    var timer = input[0].split(",").map { it.toInt() }

    val fishCountsInt: Map<Int, Int> = timer.groupingBy { it }.eachCount()
    var fishCounts: Map<Int, BigInteger> = fishCountsInt.map { it.key to it.value.toBigInteger() }.toMap()
    println(fishCounts)

    val days = 256

//    timer = part1(days, timer)
    (0 until days).forEach {
        val newCounts = mutableMapOf<Int, BigInteger>()
        val newFish = fishCounts[0] ?: ZERO
        fishCounts.forEach {
            if (it.key != 0) {
                newCounts[it.key - 1] = it.value
            }
        }

        newCounts[8] = (newCounts[8] ?: ZERO).plus(newFish)
        newCounts[6] = (newCounts[6] ?: ZERO).plus(newFish)

        fishCounts = newCounts
//        println(fishCounts)
    }

    println(fishCounts)
    println(fishCounts.values.fold(ZERO) { acc, el -> acc+ el})
}

private fun part1(days: Int, timer: List<Int>): List<Int> {
    var timer1 = timer
    (0 until days).forEach { day ->
        var newFishies = 0
        val newTimer = timer1.map {
            if (it == 0) {
                newFishies += 1
                6
            } else {
                it - 1
            }
        }.toMutableList()


        newTimer.addAll((0 until newFishies).map { 8 })

//        println("$day -> $newTimer")

        timer1 = newTimer
    }
    return timer1
}
