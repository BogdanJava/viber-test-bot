package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.WebhookEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "webhook")
class WebhookCallback(objectMapper: ObjectMapper) : ViberCallback<WebhookEvent>(objectMapper) {
    override fun process(request: Request) {
        println("Webhook setup request received")
    }
}