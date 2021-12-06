import kotlin.math.abs

fun main() {
//    val input = listOf(
//        "0,9 -> 5,9",
//        "8,0 -> 0,8",
//        "9,4 -> 3,4",
//        "2,2 -> 2,1",
//        "7,0 -> 7,4",
//        "6,4 -> 2,0",
//        "0,9 -> 2,9",
//        "3,4 -> 1,4",
//        "0,0 -> 8,8",
//        "5,5 -> 8,2",
//    )

    val input = readInput("Day05")
    var maxX = 0
    var maxY = 0

    val lines = input.map { line ->
        val (start, end) = line.split(" -> ")
        val linePair = start.toCoords() to end.toCoords()
        maxX = maxOf(maxX, linePair.first.first, linePair.second.first)
        maxY = maxOf(maxY, linePair.first.second, linePair.second.second)
        linePair
    }

    val grid: List<MutableList<Int>> = (0..maxY).map { MutableList(maxX + 1) { 0 } }
    part1(lines, grid)

    part2(lines, grid)

    println(grid.flatten().count { it > 1 })
}

private fun part2(
    lines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    grid: List<MutableList<Int>>
) {
    lines.only45Diagonals().forEach { line ->
        val start = if (line.first.first < line.second.first) line.first else line.second
        val end = if (start == line.first) line.second else line.first
        val direction = if (start.second > end.second) -1 else 1
        (start.first..end.first).forEachIndexed { i, x ->
            grid[start.second + (i * direction)][x] += 1
        }
    }
}

private fun part1(
    lines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    grid: List<MutableList<Int>>
) {
    lines.excludeDiagonals().forEach { line ->
        val (smallX, bigX) = line.order { it.first }
        val (smallY, bigY) = line.order { it.second }
        (smallX..bigX).forEach { x ->
            (smallY..bigY).forEach { y ->
                grid[y][x] += 1
            }
        }
    }
}

private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.order(function: (Pair<Int, Int>) -> Int): Pair<Int, Int> {
    val firstComparable = function(first)
    val secondComparable = function(second)
    return if (firstComparable > secondComparable) {
        secondComparable to firstComparable
    } else {
        firstComparable to secondComparable
    }
}

private fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.excludeDiagonals() =
    filter { it.first.first == it.second.first || it.first.second == it.second.second }

private fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.only45Diagonals() =
    filter {
        (it.first.first - it.second.first) != 0 &&
            abs((it.first.second - it.second.second) / (it.first.first - it.second.first)) == 1
    }

private fun String.toCoords(): Pair<Int, Int> {
    val coords = split(",").map { it.toInt() }
    return coords[0] to coords[1]
}
