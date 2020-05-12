package org.example.routes

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.BotEvent
import org.example.model.ViberAccount
import org.example.model.ViberMessage
import org.example.service.MessageService
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

/**
 * @author ts-bahdan.shyshkin
 */
class EventsRoute(
    private val mapper: ObjectMapper,
    private val messageService: MessageService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val event = mapper.readValue(request.body.stream, BotEvent::class.java)
        if (event.event == "message") {
            val message = ViberMessage(
                sender = ViberAccount(name = "Kek"),
                type = "text",
                text = "Response: ${event.message?.text}",
                receiver = event.sender?.id
            )
            messageService.send(message)
        }
        return Response(Status.OK)
    }
}