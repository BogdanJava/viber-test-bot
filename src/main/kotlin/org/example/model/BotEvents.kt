/**
 * @author ts-bahdan.shyshkin
 */
package org.example.model

import com.fasterxml.jackson.annotation.JsonProperty


interface ViberBotEvent

data class SubscribedEvent(
    val event: String,
    val timestamp: Long,
    val user: ViberAccount,
    @JsonProperty("message_token") val messageToken: String
) : ViberBotEvent

data class WebhookEvent(
    val event: String,
    val timestamp: Long,
    @JsonProperty("message_token") val messageToken: String
) : ViberBotEvent

data class MessageReceivedEvent(
    val event: String,
    val timestamp: Long,
    @JsonProperty("message_token") val messageToken: String,
    val sender: ViberAccount,
    val message: ViberMessage
) : ViberBotEvent

data class UnsubscribedEvent(
    val event: String,
    val timestamp: Long,
    @JsonProperty("message_token") val messageToken: String,
    @JsonProperty("user_id") val userId: String
) : ViberBotEvent