package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class EventDto @JvmOverloads constructor(
    @JsonIgnore
    var id: Long = 0L,

    @JsonProperty("action")
    var action: String = ""
) : Serializable, RepresentationModel<EventDto>()