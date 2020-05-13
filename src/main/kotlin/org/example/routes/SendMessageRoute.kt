package org.example.routes

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.service.MessageService
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response

/**
 * @author ts-bahdan.shyshkin
 */
class SendMessageRoute(
    private val mapper: ObjectMapper,
    private val messageService: MessageService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        throw RuntimeException("NOT IMPLEMENTED")
    }
}