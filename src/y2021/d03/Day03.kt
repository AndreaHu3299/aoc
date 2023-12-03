package y2021.d03

import println
import readInput

fun main() {
    val classPath = "y2021/d03"

    fun part1(input: List<String>): Int {
        val length = input[0].length
        val halfSize = (input.size / 2)
        val counts = input.fold(IntArray(length)) { acc, line ->
            line.forEachIndexed { index, c ->
                if (c == '1') acc[index]++
            }
            acc
        }
        val gamma = counts.map { if (it > halfSize) '1' else '0' }.joinToString("")
        val epsilon = counts.map { if (it > halfSize) '0' else '1' }.joinToString("")
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        // oxygen
        var pos = 0
        var list = input.toMutableList()
        while (list.size > 1) {
            val counts = list.groupingBy { it[pos] }.eachCount()
            val majority = if (counts['1']!! >= counts['0']!!) '1' else '0'
            list.removeIf { it[pos] != majority }
            pos++
        }
        val oxygen = list[0].toInt(2)

        // co2
        pos = 0
        list = input.toMutableList()
        while (list.size > 1) {
            val counts = list.groupingBy { it[pos] }.eachCount()
            val minority = if (counts['1']!! < counts['0']!!) '1' else '0'
            list.removeIf { it[pos] != minority }
            pos++
        }
        val co2 = list[0].toInt(2)

        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
