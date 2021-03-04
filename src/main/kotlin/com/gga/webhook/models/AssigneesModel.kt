package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ASSIGNEES")
data class AssigneesModel @JvmOverloads constructor(
    @Column(name = "LOGIN")
    var login: String = "",

    @Id
    @Column(name = "ASSIGNEES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "NODE_ID")
    var nodeId: String = "",

    @Column(name = "AVATAR_URL")
    var avatarUrl: String = "",

    @Column(name = "GRAVATAR_ID")
    var gravatarId: String = "",

    @Column(name = "URL")
    var url: String = "",

    @Column(name = "HTML_URL")
    var htmlUrl: String = "",

    @Column(name = "FOLLOWERS_URL")
    var followersUrl: String = "",

    @Column(name = "FOLLOWING_URL")
    var followingUrl: String = "",

    @Column(name = "GISTS_URL")
    var gistsUrl: String = "",

    @Column(name = "STARRED_URL")
    var starredUrl: String = "",

    @Column(name = "SUBSCRIPTIONS_URL")
    var subscriptionsUrl: String = "",

    @Column(name = "ORGANIZATIONS_URL")
    var organizationsUrl: String = "",

    @Column(name = "REPOS_URL")
    var reposUrl: String = "",

    @Column(name = "EVENTS_URL")
    var eventsUrl: String = "",

    @Column(name = "RECEIVED_EVENTS_URL")
    var receivedEventsUrl: String = "",

    @Column(name = "TYPE")
    var type: String = "",

    @Column(name = "SITE_ADMIN")
    var siteAdmin: Boolean = false,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUE_ID", foreignKey = ForeignKey(name = "C_ASSIGNEES_ISSUE"))
    var issue: IssueModel? = null
) : Serializable