package com.gga.webhook.models.dto

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.io.Serializable

data class OwnerDto @JvmOverloads constructor(
    @JsonPropertyOrder("login")
    var login: String = "",

    @JsonPropertyOrder("id")
    var id: Long = 0L,

    @JsonPropertyOrder("node_id")
    var nodeId: String = "",

    @JsonPropertyOrder("avatar_url")
    var avatarUrl: String = "",

    @JsonPropertyOrder("gravatar_id")
    var gravatarId: String = "",

    @JsonPropertyOrder("url")
    var url: String = "",

    @JsonPropertyOrder("html_url")
    var htmlUrl: String = "",

    @JsonPropertyOrder("followers_url")
    var followersUrl: String = "",

    @JsonPropertyOrder("following_url")
    var followingUrl: String = "",

    @JsonPropertyOrder("gists_url")
    var gistsUrl: String = "",

    @JsonPropertyOrder("starred_url")
    var starredUrl: String = "",

    @JsonPropertyOrder("subscriptions_url")
    var subscriptionsUrl: String = "",

    @JsonPropertyOrder("organizations_url")
    var organizationsUrl: String = "",

    @JsonPropertyOrder("repos_url")
    var reposUrl: String = "",

    @JsonPropertyOrder("events_url")
    var eventsUrl: String = "",

    @JsonPropertyOrder("received_events_url")
    var receivedEventsUrl: String = "",

    @JsonPropertyOrder("type")
    var type: String = "",

    @JsonPropertyOrder("site_admin")
    var siteAdmin: Boolean = false,
) : Serializable