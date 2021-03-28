package com.gga.webhook.models.dTO

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable

data class CreatorDto @JvmOverloads constructor(
    @JsonProperty("login")
    var login: String = "",

    @JsonIgnore
    var id: Long = 0L,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("avatar_url")
    var avatarUrl: String = "",

    @JsonProperty("gravatar_id")
    var gravatarId: String = "",

    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("html_url")
    var htmlUrl: String = "",

    @JsonProperty("followers_url")
    var followersUrl: String = "",

    @JsonProperty("following_url")
    var followingUrl: String = "",

    @JsonProperty("gists_url")
    var gistsUrl: String = "",

    @JsonProperty("starred_url")
    var starredUrl: String = "",

    @JsonProperty("subscriptions_url")
    var subscriptionsUrl: String = "",

    @JsonProperty("organizations_url")
    var organizationsUrl: String = "",

    @JsonProperty("repos_url")
    var reposUrl: String = "",

    @JsonProperty("events_url")
    var eventsUrl: String = "",

    @JsonProperty("received_events_url")
    var receivedEventsUrl: String = "",

    @JsonProperty("type")
    var type: String = "",

    @JsonProperty("site_admin")
    var siteAdmin: Boolean = false
) : Serializable, RepresentationModel<CreatorDto>()