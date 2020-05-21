package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.SubscribedEvent
import org.example.service.MongoService
import org.http4k.core.Request

@CallbackMetadata(event = "subscribed")
class SubscribedCallback(private val mongoService: MongoService, objectMapper: ObjectMapper) :
    ViberCallback<SubscribedEvent>(objectMapper, SubscribedEvent::class.java) {

    override fun process(request: Request, event: SubscribedEvent) {
        println("User subscribed: ${event.user}")
        mongoService.save(event.user) {
            println("User saved in DB: $it")
        }
    }

}
