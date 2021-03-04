package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class PayloadVo @JvmOverloads constructor(
    @JsonProperty("id")
    var id: Long = 0L,

    @JsonProperty("action")
    var action: String = ""
) : Serializable, RepresentationModel<PayloadVo>()
