package y2021.d03

import kotlin.math.absoluteValue
import kotlin.math.sign
import println
import readInput

fun main() {
    val classPath = "y2021/d05"

    fun part1(input: List<String>): Int {
        val points = mutableMapOf<Pair<Int, Int>, Int>()
        input
            .forEach {
                val line = it.split(" -> ").map { c -> c.split(",").let { (x, y) -> Pair(x.toInt(), y.toInt()) } }

                if (line[0].second == line[1].second) {
                    val x = listOf(line[0].first, line[1].first)
                    val min = x.min()
                    val max = x.max()
                    for (i in min..max) {
                        val p = Pair(i, line[0].second)
                        if (points.containsKey(p)) {
                            points[p] = points[p]!! + 1
                        } else {
                            points[p] = 1
                        }
                    }
                } else if (line[0].first == line[1].first) {
                    val y = listOf(line[0].second, line[1].second)
                    val min = y.min()
                    val max = y.max()
                    for (i in min..max) {
                        val p = Pair(line[0].first, i)
                        if (points.containsKey(p)) {
                            points[p] = points[p]!! + 1
                        } else {
                            points[p] = 1
                        }
                    }
                }
            }
        return points.values.count { it > 1 }
    }

    fun part2(input: List<String>): Int {

        val points = mutableMapOf<Pair<Int, Int>, Int>()
        input
            .forEach {
                val line = it.split(" -> ").map { c -> c.split(",").let { (x, y) -> Pair(x.toInt(), y.toInt()) } }

                if (line[0].second == line[1].second) {
                    val x = listOf(line[0].first, line[1].first)
                    val min = x.min()
                    val max = x.max()
                    for (i in min..max) {
                        val p = Pair(i, line[0].second)
                        if (points.containsKey(p)) {
                            points[p] = points[p]!! + 1
                        } else {
                            points[p] = 1
                        }
                    }
                } else if (line[0].first == line[1].first) {
                    val y = listOf(line[0].second, line[1].second)
                    val min = y.min()
                    val max = y.max()
                    for (i in min..max) {
                        val p = Pair(line[0].first, i)
                        if (points.containsKey(p)) {
                            points[p] = points[p]!! + 1
                        } else {
                            points[p] = 1
                        }
                    }
                } else if ((line[0].first - line[1].first).absoluteValue == (line[0].second - line[1].second).absoluteValue) {
                    val diff = (line[0].first - line[1].first).absoluteValue
                    val deltaX = (line[0].first - line[1].first).sign
                    val deltaY = (line[0].second - line[1].second).sign
                    for (i in 0..diff) {
                        val p = Pair(line[1].first + deltaX * i, line[1].second + deltaY * i)
                        if (points.containsKey(p)) {
                            points[p] = points[p]!! + 1
                        } else {
                            points[p] = 1
                        }
                    }
                }
            }
        return points.values.count { it > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
