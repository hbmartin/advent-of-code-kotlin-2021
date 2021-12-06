fun main() {
//    val input = listOf(
//        "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
//        "",
//        "22 13 17 11  0",
//        "8  2 23  4 24",
//        "21  9 14 16  7",
//        "6 10  3 18  5",
//        "1 12 20 15 19",
//        "",
//        "3 15  0  2 22",
//        "9 18 13 17  5",
//        "19  8  7 25 23",
//        "20 11 10 24  4",
//        "14 21 16 12  6",
//        "",
//        "14 21 17 24  4",
//        "10 16 15  9 19",
//        "18  8 23 26 20",
//        "22 11 13  6  5",
//        "2  0 12  3  7",
//    ).toMutableList()

    val input = readInput("Day04").toMutableList()

    val draws = input.removeFirst().split(",").map { it.toInt() }
    val boards: List<List<List<Int>>> =
        input.chunked(6)
            .map { board ->
                board.subList(1, board.size)
                    .map { row ->
                        row.split("\\s+".toRegex()).filter { it.isNotEmpty() }.map { it.toInt() }
                    }
            }
    val marked: List<List<MutableList<Boolean>>> = boards.map { board ->
        (board.indices).map {
            MutableList(board[0].size) { false }
        }
    }

//    part1(draws, marked, boards)

    val winners = mutableListOf<Int>()
    var lastDraw = -1
    for (draw in draws) {
        lastDraw = draw
        marked.mark(boards, draw, winners)
        val newWinners = marked.findWinners(winners)
        winners.addAll(newWinners)

        if (winners.size == boards.size) break
    }

    val lastWinner = winners.last()
    val unmarkedValues = boards[lastWinner].masked(marked[lastWinner]).sum()
    println(unmarkedValues * lastDraw)
}

private fun List<List<MutableList<Boolean>>>.findWinners(
    winners: MutableList<Int>
): List<Int> {
    val newWinners = mutableListOf<Int>()
    for (boardIndex in this.indices) {
        if (winners.contains(boardIndex)) continue
        val board = this[boardIndex]
        if (board.hasWinner() || board.rotate(false).hasWinner()) {
            newWinners.add(boardIndex)
        }
    }
    return newWinners
}

private fun part1(
    draws: List<Int>,
    marked: List<List<MutableList<Boolean>>>,
    boards: List<List<List<Int>>>
) {
    for (draw in draws) {
        marked.mark(boards, draw)
        val winner = marked.findWinner()
        if (winner != null) {
            println(winner)
            val unmarkedValues = boards[winner].masked(marked[winner]).sum()
            println(unmarkedValues * draw)
            break
        }
    }
}

private fun <E> List<List<E>>.masked(mask: List<List<Boolean>>): List<E> {
    return mapIndexed { rowIndex, row ->
        row.filterIndexed { columnIndex, _ ->
            !mask[rowIndex][columnIndex]
        }
    }.flatten()
}

private fun List<List<MutableList<Boolean>>>.findWinner(): Int? {
    for (boardIndex in this.indices) {
        val board = this[boardIndex]
        if (board.hasWinner() || board.rotate(false).hasWinner()) {
            return boardIndex
        }
    }
    return null
}

private fun List<List<Boolean>>.hasWinner(): Boolean {
    for (rowIndex in this.indices) {
        val row = this[rowIndex]
        if (row.all { it }) {
            return true
        }
    }
    return false
}

private fun <E> List<List<E>>.rotate(default: E): List<List<E>> {
    val row = this[0].size
    val column = this.size
    val transpose = MutableList(column) { MutableList<E>(row) { default } }
    for (i in 0 until row) {
        for (j in 0 until column) {
            transpose[j][i] = this[i][j]
        }
    }

    return transpose
}

private fun List<List<MutableList<Boolean>>>.mark(
    boards: List<List<List<Int>>>,
    draw: Int,
    winners: List<Int>? = null
) {
    outer@ for (boardIndex in boards.indices) {
        // no need to mark boards that have already won
        if (winners?.contains(boardIndex) == true) continue

        val board = boards[boardIndex]
        for (rowIndex in board.indices) {
            val row = board[rowIndex]
            val foundIndex = row.indexOfFirst { it == draw }
             if (foundIndex != -1) {
                this[boardIndex][rowIndex][foundIndex] = true
                continue@outer
            }
        }
    }
}
