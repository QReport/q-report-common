package ru.redenergy.report.common.entity

import org.junit.Test

import org.junit.Assert.*
import ru.redenergy.report.common.TicketReason
import ru.redenergy.report.common.TicketStatus
import java.util.*

class TicketKtTest {

    @Test
    fun testCountReasons() {
        val tickets = arrayListOf<Ticket>()

        var ticketIndex = 0
        for((amount, reason) in TicketReason.values().withIndex()){
            for(index in 0..amount){
                tickets.add(Ticket(ticketIndex++, TicketStatus.OPEN, "user", reason, arrayListOf(TicketMessage("user", "text"))))
            }
        }

        val result = tickets.countReasons()
        assertEquals(result[TicketReason.BUG], 1)
        assertEquals(result[TicketReason.GRIEFING], 2)
        assertEquals(result[TicketReason.SUGGESTION], 3)
        assertEquals(result[TicketReason.OTHER], 4)
    }

    @Test
    fun testActiveUsers() {
        val tickets = arrayListOf<Ticket>();

        var ticketIndex = 0
        //create a list with 15 tickets per 5 users
        for((amount, user) in arrayOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth").withIndex()){
            for(index in 0..amount){
                tickets.add(Ticket(ticketIndex++, TicketStatus.OPEN, user, TicketReason.OTHER,
                        arrayListOf(TicketMessage(user, "Text"))))
            }
        }

        //get five most active users
        val users = tickets.activeUsers(5)

        //check if user with lowest tickets amount not included
        assertFalse(users.containsKey("First"))

        //check if tickets amount calculated correctly
        assertTrue(users["Second"] == 2)
        assertTrue(users["Third"] == 3)
        assertTrue(users["Fourth"] == 4)
        assertTrue(users["Fifth"] == 5)
        assertTrue(users["Sixth"] == 6)
    }

    @Test
    fun testAverageResponseTime() {
        val tickets = arrayListOf<Ticket>()

        val expected = 10000000L;

        var ticketIndex = 0
        for(i in 0..3){
            val ticket = Ticket(ticketIndex++, TicketStatus.OPEN, "sender #$i", TicketReason.OTHER,
                    arrayListOf(TicketMessage("sender #$i", "Initial"),
                                TicketMessage("admin", "response", timestamp = System.currentTimeMillis() + expected)))
            tickets.add(ticket)
        }

        val result = tickets.averageResponseTime();

        assertEquals(expected, result)
    }

    @Test
    fun testZeroAverageResponseTime(){
        val tickets = arrayListOf<Ticket>()

        val result = tickets.averageResponseTime()

        assertTrue(result == 0L)
    }

}