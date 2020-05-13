package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.MessageReceivedEvent
import org.example.model.ViberAccount
import org.example.model.ViberMessage
import org.example.service.MessageService
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "message")
class MessageCallback(private val messageService: MessageService, objectMapper: ObjectMapper) :
    ViberCallback<MessageReceivedEvent>(objectMapper, MessageReceivedEvent::class.java) {
    override fun process(request: Request) {
        val event = getEvent(request)
        val message = ViberMessage(
            sender = ViberAccount(name = "Kek"),
            type = "text",
            text = "Response: ${event.message.text}",
            receiver = event.sender.id
        )
        val response = messageService.send(message)
        println(response)
    }

}