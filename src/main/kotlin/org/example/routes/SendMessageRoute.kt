package org.example.routes

import com.fasterxml.jackson.core.type.TypeReference
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
        val parsedBody = mapper.readValue(request.body.stream, object : TypeReference<Map<String, String>>() {})
        val message = parsedBody["message"]

        throw RuntimeException("NOT IMPLEMENTED")
    }
}