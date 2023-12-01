package y2021.d02

import println
import readInput

fun main() {
    val classPath = "y2021/d02"

    fun part1(input: List<String>): Int {
        var x = 0
        var depth = 0
        input.
            map { it.split(" ") }.
            forEach {
                val (action, value) = it
                when (action) {
                    "forward" -> {
                        x += value.toInt()
                    }
                    "down" -> {
                        depth += value.toInt()
                    }
                    "up" -> {
                        depth -= value.toInt()
                    }
                }
            }

        return x * depth
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var depth = 0
        var aim = 0
        input.
        map { it.split(" ") }.
        forEach {
            val (action, value) = it
            when (action) {
                "forward" -> {
                    x += value.toInt()
                    depth += aim * value.toInt()
                }
                "down" -> {
                    aim += value.toInt()
                }
                "up" -> {
                    aim -= value.toInt()
                }
            }
        }

        return x * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()
    // check(part1(testInput) == 1)

    println("Solutions:")

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
