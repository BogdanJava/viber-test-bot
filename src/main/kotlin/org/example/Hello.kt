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
import org.example.routes.callbacks.*
import org.example.service.MessageService
import org.example.service.MongoService
import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.Http4kServer
import org.http4k.server.asServer

val parser = DefaultParser()
val options: Options = Options()
    .addOption(Option.builder("url").hasArg().required().build())
    .addOption(Option.builder("token").hasArg().required().build())
    .addOption(Option.builder("mongoHost").hasArg().build())
    .addOption(Option.builder("mongoPort").type(Int::class.java).hasArg().build())
    .addOption(Option.builder("v").longOpt("verbose").hasArg(false).build())
val http: CloseableHttpClient = HttpClientBuilder.create().build()
val mapper = jacksonObjectMapper().also {
    it.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val cmd = parser.parse(options, args)

    val webhookURL = cmd.getOptionValue("url")
    val token = cmd.getOptionValue("token")
    val verbose = cmd.hasOption("v")
    val mongoHost = cmd.getOptionValue("mongoHost") ?: "localhost"
    val mongoPort = cmd.getParsedOptionValue("mongoPort") as? Int ?: 27017

    val messageService = MessageService(mapper, http, token)
    val mongoService = MongoService(mongoHost, mongoPort)

    val callbackResolver = CallbackResolver(
        MessageCallback(messageService, mapper),
        SubscribedCallback(mongoService, mapper),
        WebhookCallback(mapper),
        UnsubscribedCallback(mapper, mongoService)
    )

    val app = routes(
        "/" bind Method.POST to EventsRoute(mapper, callbackResolver, verbose),
        "/message" bind Method.POST to SendMessageRoute(mapper, messageService)
    )

    app.asServer(ApacheServer(8080)).start().also {
        connectToBot(it, webhookURL, token, verbose)
        println("Server started on port ${it.port()}. Webhook is set to \"${webhookURL}\"")
    }
}

fun connectToBot(server: Http4kServer, webhookURL: String, token: String, verbose: Boolean) {
    try {
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
        server.stop()
    }
}

fun setWebhook(webhookURL: String, token: String, verbose: Boolean): WebhookResponse {
    val request = HttpPost("https://chatapi.viber.com/pa/set_webhook")
    val json = mapper.writeValueAsString(webhookSetup(webhookURL))
    request.entity = StringEntity(json).also { it.setContentType("application/json") }
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

