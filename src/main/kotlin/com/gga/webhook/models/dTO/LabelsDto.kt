package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class LabelsDto @JvmOverloads constructor(
    @JsonIgnore
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
) : Serializable, RepresentationModel<LabelsDto>(){

    @Schema(hidden = true)
    @Suppress("unused")
    private val links: List<Links> = emptyList()

}