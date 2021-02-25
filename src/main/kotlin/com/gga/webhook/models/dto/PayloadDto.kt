package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class PayloadDto(
    @JsonProperty("id")
    var id: Long = 0L,

    @JsonProperty("action")
    var action: String = "",

    @JsonProperty("issue")
    var issue: IssueDto? = null,

    @JsonProperty("repository")
    var repository: RepositoryDto? = null,

    @JsonProperty("sender")
    var sender: SenderDto? = null
) : Serializable
