fun main() {
    val input = readInput("Day03")

//    val input = listOf(
//        "00100",
//        "11110",
//        "10110",
//        "10111",
//        "10101",
//        "01111",
//        "00111",
//        "11100",
//        "10000",
//        "11001",
//        "00010",
//        "01010",
//    )

//    val mostCommonBits = mostCommonBits(input)
//    part1(mostCommonBits)

    val mostCommonBitsInput = mostCommonInputFromBits(input, true)[0]
    val mostCommonBitsNumber = mostCommonBitsInput.map { Character.getNumericValue(it) }.toInt()
    println("$mostCommonBitsInput -> $mostCommonBitsNumber")

    val leastCommonBitsInput = mostCommonInputFromBits(input, false)[0]
    val leastCommonBitsNumber = leastCommonBitsInput.map { Character.getNumericValue(it) }.toInt()
    println("$leastCommonBitsInput -> $leastCommonBitsNumber")

    println("product : ${leastCommonBitsNumber * mostCommonBitsNumber}")
}

private fun mostCommonInputFromBits(input: List<String>, trueBits: Boolean): List<String> {
    var currentInputs = input.toMutableList()
    var iteration = 0
    while (currentInputs.size > 1) {
        val mostCommonBits = mostCommonBits(currentInputs, true).let {
            if (trueBits) it else it.inverse()
        }
        currentInputs = currentInputs.filter {
            Character.getNumericValue(it[iteration]) == mostCommonBits[iteration]
        }.toMutableList()
        iteration += 1
    }
    return currentInputs
}

private fun part1(mostCommonBits: List<Int>) {
    val gamma = mostCommonBits.toInt()
    println("gamma : $gamma")
    val epsilon = mostCommonBits.inverse().toInt()
    println("epsilon : $epsilon")
    println("product: ${epsilon * gamma}")
}

private fun mostCommonBits(input: List<String>, default: Boolean = false): List<Int> {
    val mostCommonBits = MutableList(input[0].length) { 0 to 0 }

    input.forEach { inputString ->
        inputString.forEachIndexed { index, char ->
            val current = mostCommonBits[index]
            if (char == '0') {
                mostCommonBits[index] = current.copy(first = current.first + 1)
            } else {
                mostCommonBits[index] = current.copy(second = current.second + 1)
            }
        }
    }
    return mostCommonBits.map { it.greatest(default) }
}

private fun List<Int>.inverse(): List<Int> = map { it xor 1 }

private fun Iterable<Int>.toInt(): Int =
    reversed().foldIndexed(0) { index, acc, bit ->
        acc or (bit shl index)
    }

private fun <A : Comparable<A>> Pair<A, A>.greatest(
    default: Boolean
): Int = when {
    first == second -> if (default) 1 else 0
    (first < second) -> 1
    else -> 0
}

