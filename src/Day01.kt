fun main() {
    val input = readInput("Day01").mapNotNull { it.toIntOrNull() }
//    val increases = part1(input)
//    println("increases: $increases")
    val slidingIncreases = part2(input)
    println("slidingIncreases: $slidingIncreases")
}

private fun part1(input: List<Int>): Int {

//    val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    val increases = input
        .mapIndexed { index, value ->
            if (index > 0) {
                input[index - 1] < value
            } else {
                false
            }
        }
        .count { it }
    return increases
}

private fun part2(input: List<Int>): Int {
    //     val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
    return part1(input.windowed(3).map { it.sum() })
}
