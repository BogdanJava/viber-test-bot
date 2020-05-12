package org.example.routes.callbacks

import kotlin.reflect.full.findAnnotation

/**
 * @author ts-bahdan.shyshkin
 */
@ExperimentalStdlibApi
class CallbackResolver(vararg callbacks: ViberCallback) {
    private val callbackMap: MutableMap<String, ViberCallback> = mutableMapOf()

    fun resolve(name: String) = callbackMap[name]

    init {
        callbacks.forEach {
            val clazz = it::class
            clazz.findAnnotation<CallbackMetadata>()?.let { meta ->
                callbackMap[meta.event] = it
            }
        }
    }
}