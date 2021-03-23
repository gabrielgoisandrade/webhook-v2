package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class PayloadDto @JvmOverloads constructor(
    @JsonProperty("action")
    var action: String = "",

    @JsonProperty("issue")
    var issue: IssueDto? = null,

    @JsonProperty("repository")
    var repository: RepositoryDto? = null,

    @JsonProperty("sender")
    var sender: SenderDto? = null
) : Serializable
