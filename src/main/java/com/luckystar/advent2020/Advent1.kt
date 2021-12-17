package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_1.txt").readText()
    val lines = file.split("\r\n")

    val numbers = lines.map { line -> line.toInt() }

    // part 1
    numbers.forEach { num1 ->
        numbers.forEach { num2 ->
            if (num1 + num2 == 2020) {
                println(num1 * num2)
            }
        }
    }

    // part 2
    numbers.forEach { num1 ->
        numbers.forEach { num2 ->
            numbers.forEach { num3 ->
                if (num1 + num2 + num3 == 2020) {
                    println(num1 * num2 * num3)
                }
            }
        }
    }
}