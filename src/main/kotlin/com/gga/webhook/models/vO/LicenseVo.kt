package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class LicenseVo @JvmOverloads constructor(
    @JsonIgnore
    @JsonProperty("id")
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
) : Serializable, RepresentationModel<LicenseVo>()