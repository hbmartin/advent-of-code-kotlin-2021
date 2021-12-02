fun main() {
    val input = readInput("Day02")
    val commands = input.mapNotNull {
        if (it.isEmpty()) {
            null
        } else {
            val command = it.split(" ")
            val direction = Direction.valueOf(command[0].uppercase())
            val units = command[1].toInt()
            direction to units
        }
    }

//    val (horizontalDistance, depthDistance) = part1(commands)
//    println("horizontalDistance = $horizontalDistance , depthDistance = $depthDistance")
//    println("product = ${horizontalDistance * depthDistance}")

    val position = commands.fold(Position()) { position, command ->
        when (command.first) {
            Direction.FORWARD -> {
                position.copy(
                    horizontal = position.horizontal + command.second,
                    depth = position.depth + (position.aim * command.second)
                )
            }
            Direction.DOWN -> position.copy(aim = position.aim + command.second)
            Direction.UP -> position.copy(aim = position.aim - command.second)
        }
    }
    println("position: $position")
    println("product = ${position.horizontal * position.depth}")
}

private fun part1(commands: List<Pair<Direction, Int>>): Pair<Int, Int> {
    val (horizontalCommands, depthCommands) = commands.splitList { it.first == Direction.FORWARD }

    val horizontalDistance = horizontalCommands.sumOf { it.second }
    val depthDistance = depthCommands.sumOf { if (it.first == Direction.DOWN) it.second else -it.second }
    return Pair(horizontalDistance, depthDistance)
}

private fun <T> List<T>.splitList(firstCondition: (T) -> Boolean): Pair<List<T>, List<T>> {
    val list1 = mutableListOf<T>()
    val list2 = mutableListOf<T>()
    forEach {
        if (firstCondition(it)) {
            list1.add(it)
        } else {
            list2.add(it)
        }
    }
    return list1 to list2
}

enum class Direction {
    FORWARD, DOWN, UP
}

data class Position(
    val horizontal: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0,
)
