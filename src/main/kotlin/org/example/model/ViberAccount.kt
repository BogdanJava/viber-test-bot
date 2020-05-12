package org.example.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author ts-bahdan.shyshkin
 */
data class ViberAccount(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val country: String? = null,
    val language: String? = null,
    @JsonProperty("api_version") val apiVersion: Int? = null
)