package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class PayloadDto(
    @JsonProperty("action")
    var action: String,

    @JsonProperty("issue")
    var issue: IssueDto,

    @JsonProperty("repository")
    var repository: RepositoryDto,

    @JsonProperty("sender")
    var sender: SenderDto
) : Serializable
