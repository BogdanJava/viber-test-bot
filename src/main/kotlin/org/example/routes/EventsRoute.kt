package org.example.routes

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.routes.callbacks.CallbackResolver
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

/**
 * @author ts-bahdan.shyshkin
 */
@ExperimentalStdlibApi
class EventsRoute(
    private val mapper: ObjectMapper,
    private val callbackResolver: CallbackResolver,
    private val verbose: Boolean
) : HttpHandler {
    override fun invoke(request: Request): Response {
        if (verbose) {
            println(request)
        }
        val event = mapper.readValue(request.bodyString(), Map::class.java)
        val eventName = event["event"]!! as String
        val callback = callbackResolver.resolve(eventName)
        if (callback != null) {
            try {
                callback.process(request)
            } catch (e: Throwable) {
                if (verbose) {
                    e.printStackTrace()
                }
                val json = mapper.writeValueAsString(mapOf("reason" to e.message))
                return Response(Status.INTERNAL_SERVER_ERROR).body(json)
            }
        } else {
            println("Callback for \"$eventName\" is not defined")
        }
        return Response(Status.OK)
    }
}