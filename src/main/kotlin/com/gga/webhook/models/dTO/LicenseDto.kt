package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class LicenseDto @JvmOverloads constructor(
    @JsonIgnore
    var id: Long = 0L,

    @JsonProperty("key")
    var key: String = "",

    @JsonProperty("name")
    var name: String = "",

    @JsonProperty("spdx_id")
    var spdxId: String = "",

    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("node_id")
    var nodeId: String = ""
) : Serializable, RepresentationModel<LicenseDto>()