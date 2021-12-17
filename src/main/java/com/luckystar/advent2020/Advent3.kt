package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_3.txt").readText()
    val lines = file.split("\r\n")

    val data = lines.map { line ->
        line.toCharArray().map { c ->
            when (c) {
                '.' -> false
                else -> true
            }
        }
    }

    // part 1
    fun countTrees(right: Int, bottom: Int): Int {
        var x = 0
        var result = 0
        data.forEachIndexed { index, row ->
            if (index % bottom == 0) {
                if (row.size <= x) {
                    x %= row.size
                }
                val tree = row[x]
                x += right
                if (tree) {
                    result++
                }
            }
        }
        return result
    }

    println(countTrees(3, 1))

    // part 2
    println(
            countTrees(1, 1)
            * countTrees(3, 1)
            * countTrees(5, 1)
            * countTrees(7, 1)
            * countTrees(1, 2)
    )
}

