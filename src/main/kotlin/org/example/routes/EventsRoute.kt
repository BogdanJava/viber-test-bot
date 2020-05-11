package org.example.routes

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

/**
 * @author ts-bahdan.shyshkin
 */
class EventsRoute : HttpHandler {
    override fun invoke(request: Request): Response {
        println(request.toMessage())
        return Response(Status.OK)
    }
}