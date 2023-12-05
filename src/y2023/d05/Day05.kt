package y2023.d05

import println
import readInput

class Seed(val min: Long, val max: Long)

class Range(
    private val fromMin: Long,
    private val fromMax: Long,
    private val toMin: Long,
    private val toMax: Long
) {
    constructor(domainMin: Long, codomainMin: Long, length: Long) :
        this(domainMin, domainMin + length - 1, codomainMin, codomainMin + length - 1)

    override fun toString(): String {
        return "[$fromMin, $fromMax] -> [$toMin, $toMax]"
    }

    fun contains(value: Long): Boolean {
        return value in fromMin..fromMax
    }

    fun map(value: Long): Long {
        return if (contains(value)) toMin + (value - fromMin) else value
    }

    fun map(seed: Seed): List<Pair<Boolean, Seed>> {
        if (seed.min == fromMin) {
            return if (seed.max <= fromMax) {
                listOf(Pair(true, Seed(map(seed.min), map(seed.max))))
            } else {
                listOf(
                    Pair(true, Seed(map(seed.min), map(fromMax))),
                    Pair(false, Seed(map(fromMax + 1), map(seed.max))),
                )
            }
        } else if (seed.min < fromMin) {
            if (seed.max in fromMin..fromMax) {
                return listOf(
                    Pair(false, Seed(seed.min, fromMin - 1)),
                    Pair(true, Seed(map(fromMin), map(seed.max))),
                )
            }
            if (seed.max > fromMax) {
                return listOf(
                    Pair(false, Seed(seed.min, fromMin - 1)),
                    Pair(true, Seed(map(fromMin), map(fromMax))),
                    Pair(false, Seed(fromMax + 1, seed.max)),
                )
            }
            return listOf(Pair(false, seed))
        } else /*if (seed.min > fromMin)*/ {
            if (seed.min > fromMax) {
                return listOf(Pair(false, seed))
            }
            // if(seed.min <= fromMax) {
            if (seed.max <= fromMax) {
                return listOf(Pair(true, Seed(map(seed.min), map(seed.max))))
            }
            // if (seed.max > fromMax) {
            return listOf(
                Pair(true, Seed(map(seed.min), map(fromMax))),
                Pair(false, Seed(fromMax + 1, seed.max)),
            )
        }
    }
}

class Mapper(private val ranges: List<Range>) {
    fun map1(seeds: List<Long>): List<Long> {
        return seeds.map { seed ->
            ranges.firstOrNull { it.contains(seed) }?.map(seed) ?: seed
        }
    }

    fun map2(seeds: List<Seed>): List<Seed> {
        var result = mutableListOf<Seed>()
        seeds.map { seed ->
            var domain = mutableListOf(seed)

            ranges.forEach { range ->
                val newDomain = mutableListOf<Seed>()
                domain.forEach { domainSeed ->
                    range.map(domainSeed).forEach { (isMapped, seed) ->
                        if (isMapped) {
                            result.add(seed)
                        } else {
                            newDomain.add(seed)
                        }
                    }
                }
                domain = newDomain
            }

            result = result.plus(domain).toMutableList()
        }
        return result
    }

    override fun toString(): String {
        return ranges.toString() + "\n"
    }
}

fun main() {
    val classPath = "y2023/d05"

    fun parseMaps(input: List<String>): List<Mapper> {
        val mappers = mutableListOf<Mapper>()
        var ranges = mutableListOf<Range>()
        for (line in input) {
            if (line.isEmpty()) {
                val mapper = Mapper(ranges)
                mappers.add(mapper)
                ranges = mutableListOf()
                continue
            }

            if (!line[0].isDigit()) {
                continue
            }

            val (dest, source, range) = line.split(" ").map { it.toLong() }
            ranges.add(Range(source, dest, range))
        }
        mappers.add(Mapper(ranges))
        return mappers
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }

        val maps = parseMaps(input.subList(2, input.size))

        return maps.fold(seeds) { it, mapper ->
            mapper.map1(it)
        }.min()
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].split(": ")[1]
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { (a, b) -> Seed(a, a + b - 1) }

        val maps = parseMaps(input.subList(2, input.size))

        return maps.fold(seeds) { it, mapper ->
            mapper.map2(it)
        }.minOf { it.min }
    }

    val testInput = readInput("${classPath}/test")
    part1(testInput).println()
    part2(testInput).println()

    val input = readInput("${classPath}/input")
    part1(input).println()
    part2(input).println()

}
