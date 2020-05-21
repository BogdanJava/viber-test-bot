package org.example.routes.callbacks

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.ViberBotEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
abstract class ViberCallback<T : ViberBotEvent>(
    private val mapper: ObjectMapper,
    private val eventClass: Class<T>
) {
    fun process(request: Request) {
        val event = getEvent(request)
        return process(request, event)
    }

    protected abstract fun process(request: Request, event: T)

    private fun getEvent(request: Request): T = mapper.readValue(request.bodyString(), eventClass)
}