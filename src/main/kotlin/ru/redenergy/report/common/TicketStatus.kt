package ru.redenergy.report.common

import java.awt.Color


/**
 * Represents status of ticket
 *
 * @constructor
 * @param translateKey - used on client to localize status
 * @param color - color, which should be used when displaying status in order to provide better experience
 */
enum class TicketStatus(val translateKey: String, val color: Color) {

    /**
     * The ticket has just been received and have not been reviewed
     */
    OPEN("ticket.status.open", Color(255, 255, 52)),
    /**
     * The ticket has been reviewed by moderator and currently in progress of clarification/fixing
     */
    IN_PROGRESS("ticket.status.inprogress", Color(54, 182, 255)),
    /**
     * The ticket is closed. The problem has been fixed/bad player has been punished
     */
    CLOSED("ticket.status.closed", Color(29, 232, 88));
}