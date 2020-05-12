package org.example.routes.callbacks

import org.example.model.BotEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "webhook")
class WebhookCallback : ViberCallback {
    override fun process(request: Request, event: BotEvent) {
        println("Webhook setup request received")
    }
}