package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import java.io.Serializable

data class PayloadDto(
    @Mapping("id")
    @JsonProperty("id")
    var payloadId: Long = 0L,

    @JsonProperty("action")
    var action: String,

    @JsonProperty("issue")
    var issue: IssueDto,

    @JsonProperty("repository")
    var repository: RepositoryDto,

    @JsonProperty("sender")
    var sender: SenderDto
) : Serializable
