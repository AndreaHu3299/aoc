package template

import kotlin.math.pow
import println
import readInput

fun main() {
    val classPath = "y2023/d04"

    fun score1(input: List<List<Int>>): Int {
        val winning = input[0]
        val nums = input[1]
        return nums
            .count { winning.contains(it) }
            .let {
                if (it == 0) 0 else 2.0.pow((it - 1).toDouble()).toInt()
            }
    }

    fun part1(input: List<String>): Int {
        return input.map { card ->
            card.split(":")[1].split("|").map { it.trim() }
                .let { parts ->
                    listOf(
                        parts[0].replace("  ", " ").split(" ").map { it.trim().toInt() },
                        parts[1].replace("  ", " ").trim().split(" ").map { it.trim().toInt() })
                }
        }.sumOf { score1(it) }
    }

    fun score2(input: List<List<Int>>): Int {
        val winning = input[0]
        val nums = input[1]
        return nums.count { winning.contains(it) }
    }

    fun part2(input: List<String>): Int {
        val cards = input.associate { card ->
            card.split(":").let { parts ->
                Pair(parts[0].trim().substring(4).trim().toInt(),
                    score2(parts[1].split("|").map { it.trim() }
                        .let {
                            listOf(
                                it[0].replace("  ", " ").split(" ").map { it.trim().toInt() },
                                it[1].replace("  ", " ").trim().split(" ").map { it.trim().toInt() })
                        }
                    ))
            }
        }
        val counts = 1.rangeTo(cards.size).associateWith { 1 }.toMutableMap()

        for (i in 1..cards.size) {
            val cardCount = counts[i]!!
            if (cards[i]!! != 0) {
                for (j in i + 1..i + cards[i]!!) {
                    counts[j] = counts[j]!! + cardCount
                }
            }
        }
        return counts.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
