package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import java.io.Serializable

data class SenderDto @JvmOverloads constructor(
    @JsonProperty("login")
    var login: String = "",

    @Mapping("id")
    @JsonProperty("id")
    var senderId: Long = 0L,

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
) : Serializable