package org.example.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author ts-bahdan.shyshkin
 */
data class ViberMessage(
    val type: String? = null,
    val text: String? = null,
    val media: String? = null,
    val location: GeoLocation? = null,
    val sender: ViberAccount? = null,
    val receiver: String? = null,
    @JsonProperty("tracking_data") val trackingData: String? = null
)

data class GeoLocation(
    val lat: Double,
    val lon: Double
)