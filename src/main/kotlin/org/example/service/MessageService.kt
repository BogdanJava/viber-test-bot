package org.example.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.example.model.ViberMessage

/**
 * @author ts-bahdan.shyshkin
 */
class MessageService(
    private val mapper: ObjectMapper,
    private val http: HttpClient,
    private val token: String
) {
    fun send(message: ViberMessage): HttpResponse {
        val request = HttpPost("https://chatapi.viber.com/pa/send_message")
        request.entity = StringEntity(mapper.writeValueAsString(message))
        request.addHeader("X-Viber-Auth-Token", token)
        request.addHeader("Content-Type", "application/json")
        return http.execute(request)
    }
}