package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import java.io.Serializable

data class LabelDto @JvmOverloads constructor(
    @Mapping("id")
    @JsonProperty("id")
    var labelId: Long = 0L,

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
) : Serializable