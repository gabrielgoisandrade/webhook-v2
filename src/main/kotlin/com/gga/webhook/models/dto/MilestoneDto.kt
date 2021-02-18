package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class MilestoneDto @JvmOverloads constructor(
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

    @JsonProperty("creator")
    var creator: CreatorDto? = null,

    @JsonProperty("open_issues")
    var openIssues: Int,

    @JsonProperty("closed_issues")
    var closedIssues: Int,

    @JsonProperty("state")
    var state: String = "",

    @JsonProperty("created_at")
    var createdAt: Date,

    @JsonProperty("updated_at")
    var updatedAt: Date? = null,

    @JsonProperty("due_on")
    var dueOn: Date? = null,

    @JsonProperty("closed_at")
    var closedAt: Date? = null
) : Serializable