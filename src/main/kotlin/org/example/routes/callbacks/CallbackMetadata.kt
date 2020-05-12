package org.example.routes.callbacks

/**
 * @author ts-bahdan.shyshkin
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CallbackMetadata(val event: String)