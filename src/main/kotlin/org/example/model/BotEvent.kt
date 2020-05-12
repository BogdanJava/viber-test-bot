package org.example.model

/**
 * @author ts-bahdan.shyshkin
 */
data class BotEvent(
    val event: String,
    val sender: ViberAccount?,
    val message: ViberMessage?
)

