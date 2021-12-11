package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_7.txt").readText()
    var rules = file.split("\r\n")

    rules = rules.filter { rule -> !rule.contains("no other bags") }

    val regex = """^(\w+ \w+) bags contain (.*)\.$""".toRegex()
    val rightRegex = """^(\d) (\w+ \w+) bags?$""".toRegex()
    val flatRules = rules.map { rule ->
        val (left, right) = regex.find(rule)!!.destructured
        right.split(", ").map { g ->
            val (num, bag) = rightRegex.find(g)!!.destructured
            Triple(left, num.toInt(), bag)
        }
    }.flatten()

    // part 1
    val mapRight = flatRules.groupBy { r -> r.third }
    var deps = mapRight.getValue("shiny gold");
    val uniqueBags = mutableSetOf<String>()
    while (deps.isNotEmpty()) {
        val lefts = deps.map { d -> d.first }
        uniqueBags.addAll(lefts)
        deps = lefts.map { l -> mapRight.get(l) }.filterNotNull().flatten()
    }
    println(uniqueBags.size)

    // part 2
    val mapLeft = flatRules.groupBy { r -> r.first }
    var deps2 = mapLeft.getValue("shiny gold").map { t -> Triple(t.first, t.second, t.third) };
    var numberOfBags = 0
    while (deps2.isNotEmpty()) {
        numberOfBags += deps2
                .map { q ->
                    val first = mapLeft.get(q.third)
                    Pair(first, q.second) }
                .filter { p -> p.first == null }
                .map { p -> p.second }
                .sum()
        deps2 = deps2
                .map { q -> Pair(mapLeft.get(q.third), q.second) }
                .filter { p -> p.first != null }
                .flatMap { p ->
                    numberOfBags += p.second
                    p.first!!.map { t -> Triple(t.first, t.second * p.second, t.third) }
                }
    }
    println(numberOfBags)


}

data class Quadruple<T1, T2, T3, T4>(val first: T1, val second: T2, val third: T3, val fourth: T4)