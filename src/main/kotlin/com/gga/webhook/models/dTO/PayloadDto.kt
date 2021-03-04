package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class PayloadDto @JvmOverloads constructor(
    @JsonIgnore
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
) : Serializable, RepresentationModel<PayloadDto>()
