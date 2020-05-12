package org.example.routes.callbacks

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner

/**
 * @author ts-bahdan.shyshkin
 */
class CallbackResolver(private vararg val callbacks: ViberCallback) {
    private val callbackMap: MutableMap<String, Class<out ViberCallback>> = mutableMapOf()

    fun resolve(name: String): ViberCallback? {
        return callbackMap[name]?.let { clazz ->
            callbacks.find { clazz.isAssignableFrom(it::class.java) }
        }
    }

    init {
        val reflections = Reflections("org.example.routs.callbacks", SubTypesScanner())
        reflections.getSubTypesOf(ViberCallback::class.java).forEach {
            if (!it.isAnnotationPresent(CallbackMetadata::class.java)) {
                throw RuntimeException("Class ${it.name} is missing @CallbackMetadata")
            } else {
                val metadata = it.getAnnotation(CallbackMetadata::class.java)
                callbackMap[metadata.event] = it
            }
        }
    }
}