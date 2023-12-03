package y2023.d01

import println
import readInput

fun main() {
    val classPath = "y2023/d01"

    // fun part1(input: List<String>): Int {
    //     return input
    //         .map { line ->
    //             val digits = line
    //                 .mapNotNull { it.digitToIntOrNull() }
    //             val first = digits.first()
    //             val last = digits.last()
    //
    //             listOf(first, last).joinToString(separator = "")
    //                 .toInt()
    //         }
    //         .sum()
    // }
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.find { it.isDigit() }?.toString() ?: "0"
            val lastDigit = line.findLast { it.isDigit() }?.toString() ?: "0"
            "$firstDigit$lastDigit".toInt()
        }
    }

    val lookupTable = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        1 to 1,
        2 to 2,
        3 to 3,
        4 to 4,
        5 to 5,
        6 to 6,
        7 to 7,
        8 to 8,
        9 to 9,
    )

    fun part2(input: List<String>): Int {
        return input
            .map {
                val first = lookupTable.keys
                    .mapNotNull { key -> it.indexOf(key.toString()).let { if (it == -1) null else Pair(key, it) } }
                    .minBy { it.second }
                    .let { lookupTable[it.first] }

                val last = lookupTable.keys
                    .mapNotNull { key -> it.lastIndexOf(key.toString()).let { if (it == -1) null else Pair(key, it) } }
                    .maxBy { it.second }
                    .let { lookupTable[it.first] }

                listOf(first, last).joinToString(separator = "")
                    .toInt()
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    // part1(testInput).println()
    part2(testInput).println()
    // check(part1(testInput) == 1)

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
