package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.Instant

data class MilestoneVo @JvmOverloads constructor(
    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("html_url")
    var htmlUrl: String = "",

    @JsonProperty("labels_url")
    var labelsUrl: String = "",

    @JsonProperty("id")
    var id: Long = 0,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("number")
    var number: Long = 0,

    @JsonProperty("title")
    var title: String = "",

    @JsonProperty("description")
    var description: String? = null,

    @JsonProperty("open_issues")
    var openIssues: Int = 0,

    @JsonProperty("closed_issues")
    var closedIssues: Int = 0,

    @JsonProperty("state")
    var state: String = "",

    @JsonProperty("created_at")
    var createdAt: Instant? = null,

    @JsonProperty("updated_at")
    var updatedAt: Instant? = null,

    @JsonProperty("due_on")
    var dueOn: Instant? = null,

    @JsonProperty("closed_at")
    var closedAt: Instant? = null
) : Serializable, RepresentationModel<MilestoneVo>()