package y2021.d01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        // take 2 elements in input at a time
        return input.windowed(2, 1, false).map {
            val (a, b) = it
            if (b.toInt() > a.toInt()) 1 else 0
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(3, 1, false).map {
                it.sum()
            }
            .windowed(2, 1, false).map {
                val (a, b) = it
                if (b > a) 1 else 0
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("y2021/d01/test")
    part1(testInput).println()
    // check(part1(testInput) == 7)

    val input = readInput("y2021/d01/input")
    part1(input).println()
    part2(input).println()
}
