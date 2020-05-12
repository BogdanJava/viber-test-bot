package org.example.routes.callbacks

import org.apache.http.HttpResponse
import org.example.model.BotEvent
import org.http4k.core.Request

@CallbackMetadata(event = "subscribed")
class SubscribedCallback : ViberCallback {
    override fun process(request: Request, event: BotEvent): HttpResponse {
        println("User subscribed: ${event.sender?.name}")
        TODO("Not yet implemented")
    }

}
