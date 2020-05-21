package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.UnsubscribedEvent
import org.example.service.MongoService
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "unsubscribed")
class UnsubscribedCallback(
    mapper: ObjectMapper,
    private val mongoService: MongoService
) :
    ViberCallback<UnsubscribedEvent>(mapper, UnsubscribedEvent::class.java) {
    override fun process(request: Request, event: UnsubscribedEvent) {
        println("User unsubscribed: ${event.userId}")
        mongoService.deleteById(event.userId) {
            println("Delete users: ${it.deletedCount}")
        }
    }
}