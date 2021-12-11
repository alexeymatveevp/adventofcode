package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_2.txt").readText()
    val lines = file.split("\r\n")

    val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()
    val data = lines.map { line ->
        val d = regex.find(line)!!.destructured
        PwPolicy(d.component1().toInt(), d.component2().toInt(), d.component3()[0], d.component4())
    }

    // part 1
    val correctPw = data.filter { d ->
        val count = d.input.toCharArray().filter { c -> c == d.symbol }.count()
        count >= d.from && count <= d.to
    }.count()
    println(correctPw)

    // part 2
    val correctPw2 = data.filter { d ->
        val chars = d.input.toCharArray()
        if (chars.size < d.from || chars.size < d.to) {
            false
        } else {
            (chars[d.from-1] == d.symbol).xor(chars[d.to-1] == d.symbol)
        }
    }.count()
    println(correctPw2)
}

data class PwPolicy(val from: Int, val to: Int, val symbol: Char, val input: String)