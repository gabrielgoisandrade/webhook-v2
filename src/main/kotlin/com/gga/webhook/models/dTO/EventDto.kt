package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class EventDto @JvmOverloads constructor(
    @JsonIgnore
    var id: Long = 0L,

    @JsonProperty("action")
    var action: String = ""
) : Serializable, RepresentationModel<EventDto>() {

    @Schema(hidden = true)
    @Suppress("unused")
    private val links: List<Links> = emptyList()

}
