package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class LicenseDto @JvmOverloads constructor(
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
) : Serializable