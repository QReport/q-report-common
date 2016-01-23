package ru.redenergy.report.common.entity

import ru.redenergy.report.common.TicketReason
import ru.redenergy.report.common.TicketStatus
import java.util.*

/**
 * Represents report ticket
 * @constructor
 * @param uid - identifier of a ticket, if not provided will be generated randomly
 * @param status - current status of ticket
 * @param sender - original sender of a ticket
 * @param reason - reason of a ticket
 * @param messages - messages in ticket Note: in database this field will be persisted as json string
 * @param server - server, from which ticket was sent, used when there are more than 1 server working with one database
 */
data class Ticket(var uid: UUID = UUID.randomUUID(), var status: TicketStatus, var sender: String, var reason: TicketReason, var messages: MutableList<TicketMessage>, var server: String = "unknown") {

    /**
     * A short representation of uuid <br>
     * In general, it just returns the first 8 symbols of uuid <br>
     * Should be used as human readable uuid, because full uuid is too long to remember
     */
    val shortUid: String
        get() = this.uid.toString().substring(0, 8)

    /**
     * Empty constructor requested by ORMLite
     */
    private constructor(): this(UUID.randomUUID(), TicketStatus.OPEN, "unknown", TicketReason.OTHER, arrayListOf())

}