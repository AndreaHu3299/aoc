package y2021.d03

import println
import readInput

fun main() {
    val classPath = "y2021/d04"

    class Board {
        private var board: Array<IntArray>
        private var marked: Array<BooleanArray>

        constructor(input: List<String>) {
            val board = Array(5) { IntArray(5) }
            input
                .filter { it.isNotBlank() }
                .forEachIndexed { y, line ->
                    board[y] = line.split(' ').filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
                }
            this.board = board
            this.marked = Array(5) { BooleanArray(5) }
        }

        fun call(num: Int): Boolean {
            board.forEachIndexed { y, rows ->
                rows.forEachIndexed { x, n ->
                    if (n == num) {
                        marked[y][x] = true
                        return checkWin(y, x)
                    }
                }
            }
            return false
        }

        private fun checkWin(y: Int, x: Int): Boolean {
            // check horizontal
            return marked[y].all { it }
                // check vertical
                .or(marked.map { it[x] }.all { it })
            // check diagonal
            // .or(
            //     if (y == x)
            //         marked[0][0] && marked[1][1] && marked[2][2] && marked[3][3] && marked[4][4]
            //     else if (y + x == 4)
            //         marked[0][4] && marked[1][3] && marked[2][2] && marked[3][1] && marked[4][0]
            //     else false
            // )
        }

        fun calc(lastCalledNum: Int): Int {
            var num = 0
            board.forEachIndexed { y, rows ->
                rows.forEachIndexed { x, n ->
                    num += if (!marked[y][x]) n else 0
                }
            }
            return num * lastCalledNum
        }
    }

    fun part1(input: List<String>): Int {
        val nums = input[0].split(",").map { it.toInt() }

        val boards = input.takeLast(input.size - 2).chunked(6) { Board(it) }
        var result: List<Pair<Int, Boolean>>
        for (i in nums) {
            result = boards
                .mapIndexed { index, board -> Pair(index, board.call(i)) }
                .filter { it.second }

            if (result.isNotEmpty()) {
                return result.maxOf { boards[it.first].calc(i) }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val nums = input[0].split(",").map { it.toInt() }

        var boards = input.takeLast(input.size - 2).chunked(6) { Board(it) }
        for (i in nums) {

            if (boards.size == 1 && boards[0].call(i)) {
                return boards[0].calc(i)
            } else {
                boards = boards
                    .filter { !it.call(i) }
            }
        }
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
