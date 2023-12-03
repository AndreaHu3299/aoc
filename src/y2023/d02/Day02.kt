package y2023.d02

import println
import readInput

fun main() {
    val classPath = "y2023/d02"

    fun possible(cubes: String): Boolean {
        return cubes
            .split(",")
            .map { it.trim().split(" ") }
            .all {
                when (it[1]) {
                    "red" -> it[0].toInt() in 1..12
                    "green" -> it[0].toInt() in 1..13
                    "blue" -> it[0].toInt() in 1..14
                    else -> false
                }
            }
    }

    // The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
    fun part1(input: List<String>): Int {
        return input
            .map { Pair(it.split(":")[0].split(" ")[1], it.split(":")[1].split(";")) }
            .filter { draws -> draws.second.all { possible(it) } }
            .sumOf { it.first.toInt() }
    }

    fun getPower(sets: List<String>): Int {
        return sets
            .flatMap { it.split(",") }
            .map { Pair(it.trim().split(" ")[0], it.trim().split(" ")[1]) }
            .groupBy { it.second }
            .map { entry -> entry.value.maxOf { it.first.toInt() } }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        return input
            .map {
                it.split(":").let { x ->
                    Pair(
                        x[0].split(" ")[1],
                        x[1].split(";")
                    )
                }
            }
            .sumOf { getPower(it.second) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
