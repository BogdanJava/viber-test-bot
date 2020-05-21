package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.ViberBotEvent
import org.example.model.WebhookEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "webhook")
class WebhookCallback(objectMapper: ObjectMapper) :
    ViberCallback<WebhookEvent>(objectMapper, WebhookEvent::class.java) {
    override fun process(request: Request, event: WebhookEvent) {
        println("Webhook setup request received")
    }
}