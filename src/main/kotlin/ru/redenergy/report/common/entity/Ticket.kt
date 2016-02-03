package ru.redenergy.report.common.entity

import ru.redenergy.report.common.TicketReason
import ru.redenergy.report.common.TicketStatus
import java.util.*

/**
 * Represents report ticket
 * @constructor
 * @param uid - identifier of a ticket
 * @param status - current status of ticket
 * @param sender - original sender of a ticket
 * @param reason - reason of a ticket
 * @param messages - messages in ticket Note: in database this field will be persisted as json string
 * @param server - server, from which ticket was sent, used when there are more than 1 server working with one database
 */
data class Ticket(var uid: Int, var status: TicketStatus, var sender: String, var reason: TicketReason, var messages: MutableList<TicketMessage>, var server: String = "unknown") {

    /**
     * A short representation of uuid <br>
     * In general, it just returns the first 8 symbols of uuid <br>
     * Should be used as human readable uuid, because full uuid is too long to remember
     */
    val shortUid: String
        get() = uid.toString()

    /**
     * Empty constructor requested by ORMLite
     */
    private constructor(): this(-1, TicketStatus.OPEN, "unknown", TicketReason.OTHER, arrayListOf())

}

/**
 * Map in which key is TicketReason and value is amount of tickets in this category
 */
fun MutableList<Ticket>.countReasons(): Map<TicketReason, Int> =
        mapOf(*TicketReason.values()
            .map { r -> r to count { it.reason.equals(r) } }
            .toTypedArray())

/**
 * Map with users who send most of tickets
 * @param limiter - first N users to return
 */
fun MutableList<Ticket>.activeUsers(limiter: Int): Map<String, Int> =
        map { it.sender }
                .distinct()
                .map { it to  count { t -> t.sender.equals(it) }}
                .sortedBy { it.second }
                .reversed()
                .run { slice(0..Math.min(count() - 1, limiter - 1)) }
                .toMap()

/**
 * Average response time between first original sender's message and first response of administrator
 */
fun MutableList<Ticket>.averageResponseTime(): Long =
        filter { t -> t.messages.any { m -> m.sender != t.sender } }
        .map { it.messages.first { m -> m.sender != it.sender } .timestamp - it.messages[0].timestamp }
        .run { return if(!isEmpty()) sum() / count() else 0L }