package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import java.io.Serializable

data class LicenseDto @JvmOverloads constructor(
    @Mapping("id")
    @JsonProperty("id")
    var licenseId: Long = 0L,

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