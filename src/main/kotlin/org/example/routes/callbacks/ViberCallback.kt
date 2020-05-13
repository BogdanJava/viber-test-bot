package org.example.routes.callbacks

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.model.ViberBotEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
abstract class ViberCallback<T : ViberBotEvent>(private val mapper: ObjectMapper) {
    abstract fun process(request: Request)

    protected fun getEvent(request: Request): T = mapper.readValue(request.bodyString(),
        object : TypeReference<T>() {})
}