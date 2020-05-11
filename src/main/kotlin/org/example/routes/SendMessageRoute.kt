package org.example.routes

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import java.lang.RuntimeException

/**
 * @author ts-bahdan.shyshkin
 */
class SendMessageRoute(
    val mapper: ObjectMapper,
    val httpClient: HttpClient
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val parsedBody = mapper.readValue(request.body.stream, object : TypeReference<Map<String, String>>() {})
        val message = parsedBody["message"]
        val botRequest = HttpPost("https://chatapi.viber.com/pa/send_message")
        throw RuntimeException("NOT IMPLEMENTED")
    }
}