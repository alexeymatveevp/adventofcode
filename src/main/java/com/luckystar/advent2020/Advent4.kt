package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_4.txt").readText()
    val passports = file.split("\r\n\r\n")

    val data = passports.map { p ->
        val split = p
                .split("\r\n")
                .reduce { acc, s -> "$acc $s" }
                .split(" ")
        val parts = split
                .map { part ->
                    val pair = part.split(":")
                    Pair(pair[0], pair[1])
                }
                .associateBy({ pair -> pair.first }, { pair -> pair.second })
        Passport(parts["byr"]?.toInt(), parts["iyr"]?.toInt(), parts["eyr"]?.toInt(), parts["hgt"], parts["hcl"], parts["ecl"], parts["pid"], parts["cid"])
    }

    // part 1
    println(data.filter { passport ->
        passport.byr != null
                && passport.iyr != null
                && passport.eyr != null
                && passport.hgt != null
                && passport.hcl != null
                && passport.ecl != null
                && passport.pid != null
    }.count())

    // part 2
    val hgtRegex = """(\d+)(cm|in)""".toRegex()
    val hclRegex = """#[0-9a-f]{6}""".toRegex()
    val eclRegex = """^(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex()
    val pidRegex = """^(\d){9}$""".toRegex()
    fun validate(passport: Passport): Boolean {
        if (passport.byr != null
                && passport.iyr != null
                && passport.eyr != null
                && passport.hgt != null
                && passport.hcl != null
                && passport.ecl != null
                && passport.pid != null) {
            if (passport.byr !in 1920..2002) {
                return false
            }
            if (passport.iyr !in 2010..2020) {
                return false
            }
            if (passport.eyr !in 2020..2030) {
                return false
            }

            val hgtMatch = hgtRegex.find(passport.hgt) ?: return false
            val ( value, type ) = hgtMatch.destructured
            val hgtValue = value.toInt()
            if (type == "cm" && (hgtValue !in 150..193)
                    || type == "in" && (hgtValue !in 59..76)) {
                return false
            }

            if (hclRegex.find(passport.hcl) == null) {
                return false
            }
            if (eclRegex.find(passport.ecl) == null) {
                return false
            }
            if (pidRegex.find(passport.pid) == null) {
                return false
            }

            return true
        } else {
            return false
        }
    }
    println(data.filter(::validate).count())
}

data class Passport(
        val byr: Int?,
        val iyr: Int?,
        val eyr: Int?,
        val hgt: String?,
        val hcl: String?,
        val ecl: String?,
        val pid: String?,
        val cid: String?
)