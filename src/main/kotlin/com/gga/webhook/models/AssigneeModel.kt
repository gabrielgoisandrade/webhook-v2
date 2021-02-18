package com.gga.webhook.models

import org.hibernate.annotations.NaturalId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ASSIGNEE")
data class AssigneeModel @JvmOverloads constructor(
    @Column(name = "LOGIN")
    val login: String,

    @Id
    @Column(name = "ASSIGNEE_ID")
    val id: Long = 0L,

    @NaturalId
    @Column(name = "NODE_ID", unique = true)
    val nodeId: String,

    @Column(name = "AVATAR_URL")
    val avatarUrl: String,

    @NaturalId
    @Column(name = "GRAVATAR_ID", unique = true)
    val gravatarId: String = "",

    @Column(name = "URL")
    val url: String,

    @Column(name = "HTML_URL")
    val htmlUrl: String,

    @Column(name = "FOLLOWERS_URL")
    val followersUrl: String,

    @Column(name = "FOLLOWING_URL")
    val followingUrl: String,

    @Column(name = "GIST_URL")
    val gistsUrl: String,

    @Column(name = "STARRED_URL")
    val starredUrl: String,

    @Column(name = "SUBSCRIPTIONS_URL")
    val subscriptionsUrl: String,

    @Column(name = "ORGANIZATIONS_URL")
    val organizationsUrl: String,

    @Column(name = "REPOS_URL")
    val reposUrl: String,

    @Column(name = "EVENTS_URL")
    val eventsUrl: String,

    @Column(name = "RECEIVED_EVENTS_URL")
    val receivedEventsUrl: String,

    @Column(name = "TYPE")
    val type: String,

    @Column(name = "SITE_ADMIN")
    val siteAdmin: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "ISSUE_ID",
        referencedColumnName = "ISSUE_ID",
        foreignKey = ForeignKey(name = "C_ASSIGNEE_ISSUE")
    )
    val issue: IssueModel? = null
) : Serializable