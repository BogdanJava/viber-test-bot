package org.example

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.example.model.WebhookResponse
import org.example.routes.EventsRoute
import org.example.routes.SendMessageRoute
import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

val parser = DefaultParser()
val options: Options = Options()
    .addOption(Option.builder("url").hasArg().required().build())
    .addOption(Option.builder("token").hasArg().required().build())
    .addOption(Option.builder("v").longOpt("verbose").hasArg(false).build())
val http: CloseableHttpClient = HttpClientBuilder.create().build()
val mapper = jacksonObjectMapper().also {
    it.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

fun main(args: Array<String>) {
    val cmd = parser.parse(options, args)
    val verbose = cmd.hasOption("v")

    val app = routes(
        "/" bind Method.GET to EventsRoute(),
        "/message" bind Method.POST to SendMessageRoute(mapper, http)
    )
    app.asServer(ApacheServer(8080)).start().also {
        try {
            val webhookURL = cmd.getOptionValue("url")
            val token = cmd.getOptionValue("token")
            if (verbose) {
                println("Webhook URL: $webhookURL, bot auth token: $token")
            }
            setWebhook(webhookURL, token, verbose)
        } catch (e: Throwable) {
            if (verbose) {
                e.printStackTrace()
            }
            println(e.message)
            println("Server startup error. Shutting down...")
            it.stop()
        }
    }
}

fun setWebhook(webhookURL: String, token: String, verbose: Boolean): WebhookResponse {
    val request = HttpPost("https://chatapi.viber.com/pa/set_webhook")
    val json = mapper.writeValueAsString(webhookSetup(webhookURL))
    request.entity = StringEntity(json)
    request.addHeader("Content-Type", "application/json")
    request.addHeader("X-Viber-Auth-Token", token)
    if (verbose) {
        println(request)
        println(request.entity)
    }
    val response = http.execute(request)
    val body = mapper.readValue(response.entity.content, WebhookResponse::class.java)
    if (body.status != 0) {
        if (verbose) {
            println("Response: $response, body: $body")
        }
        throw RuntimeException(body.statusMessage)
    } else return body
}


fun webhookSetup(webhookURL: String) = mapOf(
    "url" to webhookURL,
    "event_types" to emptyArray<String>(),
    "send_name" to true,
    "send_photo" to true
)

