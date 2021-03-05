package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.Instant

data class IssueVo @JvmOverloads constructor(
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

    @JsonProperty("labels")
    var labels: Set<LabelsVo> = hashSetOf(),

    @JsonProperty("state")
    var state: String = "",

    @JsonProperty("locked")
    var locked: Boolean = false,

    @JsonProperty("assignees")
    var assignees: Set<AssigneesVo> = hashSetOf(),

    @JsonProperty("comments")
    var comments: Int = 0,

    @JsonProperty("created_at")
    var createdAt: Instant? = null,

    @JsonProperty("updated_at")
    var updatedAt: Instant? = null,

    @JsonProperty("closed_at")
    var closedAt: Instant? = null,

    @JsonProperty("author_association")
    var authorAssociation: String = "",

    @JsonProperty("active_lock_reason")
    var activeLockReason: String? = null,

    @JsonProperty("body")
    var body: String = "",

    @JsonProperty("performed_via_github_app")
    var performedViaGithubApp: String? = null
) : Serializable, RepresentationModel<IssueVo>()
