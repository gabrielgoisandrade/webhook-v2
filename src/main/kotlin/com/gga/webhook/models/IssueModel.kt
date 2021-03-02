package com.gga.webhook.models

import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "ISSUE")
data class IssueModel @JvmOverloads constructor(
    @Column(name = "URL")
    var url: String = "",

    @Column(name = "REPOSITORY_URL")
    var repositoryUrl: String = "",

    @Column(name = "LABELS_URL")
    var labelsUrl: String = "",

    @Column(name = "COMMENTS_URL")
    var commentsUrl: String = "",

    @Column(name = "EVENTS_URL")
    var eventsUrl: String = "",

    @Column(name = "HTML_URL")
    var htmlUrl: String = "",

    @Id
    @Column(name = "ISSUE_ID")
    var id: Long = 0,

    @Column(name = "NODE_ID")
    var nodeId: String = "",

    @Column(name = "NUMBER")
    var number: Int = 0,

    @Column(name = "TITLE")
    var title: String = "",

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = ForeignKey(name = "C_ISSUE_USER"))
    var user: UserModel? = null,

    @Column(name = "STATE")
    var state: String = "",

    @Column(name = "LOCKED")
    var locked: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID", foreignKey = ForeignKey(name = "C_ISSUE_ASSIGNEE"))
    var assignee: AssigneeModel? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "MILESTONE_ID", foreignKey = ForeignKey(name = "C_ISSUE_MILESTONE"))
    var milestone: MilestoneModel? = null,

    @Column(name = "COMMENTS")
    var comments: Int = 0,

    @Column(name = "CREATED_AT")
    var createdAt: Instant? = null,

    @Column(name = "UPDATED_AT")
    var updatedAt: Instant? = null,

    @Column(name = "CLOSED_AT")
    var closedAt: Instant? = null,

    @Column(name = "AUTHOR_ASSOCIATION")
    var authorAssociation: String = "",

    @Column(name = "ACTIVE_LOCK_REASON")
    var activeLockReason: String? = null,

    @Column(name = "BODY")
    var body: String = "",

    @Column(name = "PERFORMED_VIA_GITHUB_APP")
    var performedViaGithubApp: String? = null
) : Serializable
