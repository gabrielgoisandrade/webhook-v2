package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.Instant

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

    @get:JsonIgnore
    var id: Long = 0,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("number")
    var number: Int = 0,

    @JsonProperty("title")
    var title: String = "",

    @get:JsonIgnore
    @JsonProperty("user")
    var user: UserDto? = null,

    @get:JsonIgnore
    @JsonProperty("labels")
    var labels: Set<LabelsDto> = emptySet(),

    @JsonProperty("state")
    var state: String = "",

    @JsonProperty("locked")
    var locked: Boolean = false,

    @get:JsonIgnore
    @JsonProperty("assignee")
    var assignee: AssigneeDto? = null,

    @get:JsonIgnore
    @JsonProperty("assignees")
    var assignees: Set<AssigneesDto> = emptySet(),

    @get:JsonIgnore
    @JsonProperty("milestone")
    var milestone: MilestoneDto? = null,

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
) : Serializable, RepresentationModel<IssueDto>(){

    @Schema(hidden = true)
    @Suppress("unused")
    private val links: List<Links> = emptyList()

}
