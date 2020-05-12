package org.example.routes.callbacks

import org.apache.http.HttpResponse
import org.example.model.BotEvent
import org.http4k.core.Request

/**
 * @author ts-bahdan.shyshkin
 */
interface ViberCallback {
    fun process(request: Request, event: BotEvent): HttpResponse
}