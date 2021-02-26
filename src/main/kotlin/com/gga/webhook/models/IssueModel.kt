package com.gga.webhook.models

import org.hibernate.annotations.NaturalId
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

        @NaturalId
        @Column(name = "NODE_ID", unique = true)
        var nodeId: String = "",

        @Column(name = "NUMBER")
        var number: Int = 0,

        @Column(name = "TITLE")
        var title: String = "",

        @OneToOne(mappedBy = "issue")
        var user: UserModel? = null,

        @OneToMany(mappedBy = "issue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var labels: Set<LabelsModel> = hashSetOf(),

        @Column(name = "STATE")
        var state: String = "",

        @Column(name = "LOCKED")
        var locked: Boolean = false,

        @OneToOne(mappedBy = "issue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var assignee: AssigneeModel? = null,

        @OneToMany(mappedBy = "issue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var assignees: Set<AssigneesModel> = hashSetOf(),

        @OneToOne(mappedBy = "issue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
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
        var performedViaGithubApp: String? = null,

        @OneToOne(mappedBy = "issue")
        var payload: PayloadModel? = null
) : Serializable
