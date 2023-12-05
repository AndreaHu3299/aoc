package y2023.d03

import println
import readInput

class Num(val y: Int, val xMin: Int, val xMax: Int, val num: Int) {
    var adjacentPos: List<Pair<Int, Int>> = getAdjacent(this)

    override fun toString(): String {
        return "Num(y=$y, xMin=$xMin, xMax=$xMax, num=$num)"
    }

    fun nextToGear(pos: Pair<Int, Int>): Boolean {
        return adjacentPos.any { it == pos }
    }
}

fun getAdjacent(num: Num): List<Pair<Int, Int>> {
    val adjacentPos = mutableListOf<Pair<Int, Int>>()
    // top and bottom
    for (i in num.xMin - 1..num.xMax + 1) {
        adjacentPos.add(Pair(num.y - 1, i))
        adjacentPos.add(Pair(num.y + 1, i))
    }
    // left and right
    adjacentPos.add(Pair(num.y, num.xMin - 1))
    adjacentPos.add(Pair(num.y, num.xMax + 1))
    return adjacentPos
}

fun main() {
    val classPath = "y2023/d03"


    fun findNum(board: Array<CharArray>, y: Int, x: Int): Num {
        val digitArray = mutableListOf<Char>()
        var idx = x
        while (idx < board[0].size) {
            val currDigit = board[y][idx]
            if (!currDigit.isDigit()) {
                break
            }
            digitArray.add(currDigit)
            idx++
        }
        return Num(y, x, idx - 1, digitArray.joinToString("").toInt())
    }


    fun part1(input: List<String>): Int {
        val width = input[0].length
        val height = input.size
        val board = Array(width) { CharArray(height) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                board[y][x] = input[y][x]
            }
        }
        val visited = mutableSetOf<Pair<Int, Int>>()
        val numbers = mutableListOf<Num>()
        val symbols = mutableSetOf<Pair<Int, Int>>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (visited.contains(Pair(y, x))) continue
                visited.add(Pair(y, x))
                when {
                    board[y][x] == '.' -> continue
                    board[y][x].isDigit() -> {
                        val num = findNum(board, y, x)
                        numbers.add(num)
                        for (i in num.xMin..num.xMax) {
                            visited.add(Pair(y, i))
                        }
                    }

                    else -> symbols.add(Pair(y, x)) // symbol
                }
            }
        }


        return numbers
            .filterNot { it.adjacentPos.all { adj -> !symbols.contains(adj) } }
            .sumOf { it.num }
    }

    fun part2(input: List<String>): Int {
        val width = input[0].length
        val height = input.size
        val board = Array(width) { CharArray(height) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                board[y][x] = input[y][x]
            }
        }
        val visited = mutableSetOf<Pair<Int, Int>>()
        val numbers = mutableListOf<Num>()
        val gears = mutableSetOf<Pair<Int, Int>>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (visited.contains(Pair(y, x))) continue
                visited.add(Pair(y, x))
                when {
                    board[y][x] == '.' -> continue
                    board[y][x] == '*' -> gears.add(Pair(y, x))
                    board[y][x].isDigit() -> {
                        val num = findNum(board, y, x)
                        numbers.add(num)
                        for (i in num.xMin..num.xMax) {
                            visited.add(Pair(y, i))
                        }
                    }
                }
            }
        }

        return gears
            .map { gear -> Pair(gear, numbers.filter { it.nextToGear(gear) }) }
            .filter { gear -> gear.second.size == 2 }
            .sumOf { gear -> gear.second.map { it.num }.reduce { acc, num -> acc * num } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()
}
