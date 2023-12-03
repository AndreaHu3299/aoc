package y2021.d03

import println
import readInput

fun main() {
    val classPath = "y2021/d06"


    fun part1(input: List<String>, days: Int): Int {
        var fishes = input[0].split(",").map { it.toInt() }

        var nextFishes: List<Int>
        for (i in 1..days) {
            nextFishes = fishes.map { if (it == 0) 6 else it - 1 }
            fishes.count { it == 0 }.let { 1.rangeTo(it).forEach { _ -> nextFishes.addLast(8) } }
            fishes = nextFishes
        }
        return fishes.size
    }

    fun part2(input: List<String>, days: Int): Int {
        var fishes = input[0].split(",").map { it.toInt() }
        fishes.let { println(it) }
        // 18
        // 3, 4, 3, 1, 2
        // 21, 22, 21, 19, 20
        // 22 15 8 1
        // 22/7 = 3
        // newFish(daysLeft - 7 * 1) = newFish(22 - 7) = newFish(15)
        // newFish(daysLeft - 7 * 2) = newFish(22 - 14) = newFish(8)
        // newFish(daysLeft - 7 * 3) = newFish(22 - 21) = newFish(1)
        return fishes
            .map { days + (6 - it) }
            // .also { println(it) }
            .map { oldFish(it) }
            // .also { println(it) }
            .sum()

    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
// part1(testInput, 18).println()
    for (i in 1..20) {
        part2(testInput, 2).println()
    }

    val input = readInput("${classPath}/input")
// part1(input).println()
// part2(input, 80).println()
}

fun newFish(daysLeft: Int): Int {
    // println("newFish(${daysLeft})")
    if (daysLeft < 0) return 0
    return 1 + oldFish(daysLeft - 9)
}

fun oldFish(daysLeft: Int): Int {
    // println("oldFish(${daysLeft})")
    if (daysLeft < 0) return 0
    return 1 + 1.rangeTo(daysLeft / 7).map { newFish(daysLeft - 7 * it) }.sum()
}