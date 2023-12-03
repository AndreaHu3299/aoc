package y2021.d03

import println
import readInput

fun main() {
    val classPath = "y2021/d07"

    fun part1(input: List<String>): Int {
        val crabs = input[0].split(",").map { it.toInt() }
        return crabs.sum() / crabs.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
