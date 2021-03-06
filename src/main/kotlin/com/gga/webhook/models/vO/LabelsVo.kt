package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class LabelsVo @JvmOverloads constructor(
    @JsonProperty("id")
    var id: Long = 0L,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("name")
    var name: String = "",

    @JsonProperty("color")
    var color: String = "",

    @JsonProperty("default")
    var default: Boolean = false,

    @JsonProperty("description")
    var description: String? = null
) : Serializable, RepresentationModel<LabelsVo>()