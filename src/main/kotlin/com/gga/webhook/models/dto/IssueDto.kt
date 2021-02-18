package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class IssueDto @JvmOverloads constructor(
    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("repository_url")
    var repositoryUrl: String = "",

    @JsonProperty("labels_url")
    var labelsUrl: String = "",

    @JsonProperty("comments_url")
    var commentsUrl: String = "",

    @JsonProperty("events_url")
    var eventsUrl: String = "",

    @JsonProperty("html_url")
    var htmlUrl: String = "",

    @JsonProperty("id")
    var id: Long = 0,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("number")
    var number: Int = 0,

    @JsonProperty("title")
    var title: String = "",

    @JsonProperty("user")
    var user: UserDto,

    @JsonProperty("labels")
    var labels: Set<LabelDto> = hashSetOf(),

    @JsonProperty("state")
    var state: String = "",

    @JsonProperty("locked")
    var locked: Boolean = false,

    @JsonProperty("assignee")
    var assignee: AssigneeDto? = null,

    @JsonProperty("assignees")
    var assignees: Set<AssigneesDto>? = hashSetOf(),

    @JsonProperty("milestone")
    var milestone: MilestoneDto? = null,

    @JsonProperty("comments")
    var comments: Int = 0,

    @JsonProperty("created_at")
    var createdAt: Date? = null,

    @JsonProperty("updated_at")
    var updatedAt: Date? = null,

    @JsonProperty("closed_at")
    var closedAt: Date? = null,

    @JsonProperty("autor_association")
    var authorAssociation: String = "",

    @JsonProperty("active_lock_reason")
    var activeLockReason: String? = null,

    @JsonProperty("body")
    var body: String = "",

    @JsonProperty("performed_via_github_app")
    var performedViaGithubApp: String? = null
) : Serializable
