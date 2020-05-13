package org.example.routes.callbacks

import org.example.model.ViberBotEvent
import kotlin.reflect.KClass

/**
 * @author ts-bahdan.shyshkin
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CallbackMetadata(val event: String)