package com.luckystar.advent2020

fun main() {
    val file = object {}.javaClass.getResource("/2020/input_5.txt").readText()
    val tickets = file.split("\r\n")

    val data = tickets.map { t ->
        val row = Integer.parseInt(t.substring(0, 7).replace("F", "0").replace("B", "1"), 2)
        val seat = Integer.parseInt(t.substring(7).replace("R", "1").replace("L", "0"), 2)
        Ticket(row, seat)
    }

    // part 1
    val ticketIds = data.map(::toTicketId)
    val ticketMax = ticketIds.max()
    println(ticketMax)

    // part 2
    if (ticketMax != null) {
        val missingTickets = mutableListOf<Int>()
        for (i in 0..ticketMax) {
            if (!ticketIds.contains(i)) {
                missingTickets.add(i)
            }
        }
        println(missingTickets)
    }
}

fun toTicketId(t: Ticket): Int {
    return t.row * 8 + t.seat
}

data class Ticket(val row: Int, val seat: Int)