package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_6.txt").readText()
    val groups = file.split("\r\n\r\n")

    // part 1
    val uniqueAnswers = groups.map { g ->
        g.split("\r\n")
                .map { p -> p.toCharArray().toSet() }
//                .reduce { acc, questions -> acc.union(questions) }
                .reduce { acc, questions -> acc.intersect(questions) } // part 2
                .count()
    }

    println(uniqueAnswers.sum())

}
