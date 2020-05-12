package org.example.routes.callbacks

import org.example.model.BotEvent
import org.example.model.ViberAccount
import org.example.model.ViberMessage
import org.example.service.MessageService
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
@CallbackMetadata(event = "message")
class MessageCallback(private val messageService: MessageService) : ViberCallback {
    override fun process(request: Request, event: BotEvent) {
        val message = ViberMessage(
            sender = ViberAccount(name = "Kek"),
            type = "text",
            text = "Response: ${event.message?.text}",
            receiver = event.sender?.id
        )
        messageService.send(message)
    }

}