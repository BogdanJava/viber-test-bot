package org.example.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.Nullable

/**
 * @author ts-bahdan.shyshkin
 */
data class WebhookResponse(
    val status: Int,
    @JsonProperty("status_message") val statusMessage: String,
    @JsonProperty("event_types") val eventTypes: List<String>?,
    @JsonProperty("chat_hostname") val chatHostname: String?
)

