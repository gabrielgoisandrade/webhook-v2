package com.gga.webhook.models

import org.hibernate.annotations.NaturalId
import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ISSUE")
data class IssueModel @JvmOverloads constructor(
    @Column(name = "URL")
    var url: String,

    @Column(name = "REPOSITORY_URL")
    var repositoryUrl: String,

    @Column(name = "LABELS_URL")
    var labelsUrl: String,

    @Column(name = "COMMENTS_URL")
    var commentsUrl: String,

    @Column(name = "EVENTS_URL")
    var eventsUrl: String,

    @Column(name = "HTML_URL")
    var htmlUrl: String,

    @Id
    @Column(name = "ISSUE_ID")
    val id: Long = 0,

    @NaturalId
    @Column(name = "NODE_ID", unique = true)
    var nodeId: String,

    @Column(name = "NUMBER")
    var number: Int = 0,

    @Column(name = "TITLE")
    var title: String,

    @OneToOne(mappedBy = "issue")
    var user: UserModel,

    @OneToMany(mappedBy = "issue")
    var labels: Set<LabelModel> = setOf(),

    @Column(name = "STATE")
    var state: String,

    @Column(name = "LOCKED")
    var locked: Boolean = false,

    @OneToOne(mappedBy = "issue")
    var assignee: AssigneeModel? = null,

    @OneToMany(mappedBy = "issue")
    var assignees: Set<AssigneesModel>? = setOf(),

    @OneToOne(mappedBy = "issue")
    var milestone: MilestoneModel? = null,

    @Column(name = "COMMENTS")
    var comments: Int = 0,

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    var createdAt: Date,

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    var updatedAt: Date? = null,

    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.DATE)
    var closedAt: Date? = null,

    @Column(name = "AUTOR_ASSOCIATION")
    var authorAssociation: String,

    @Column(name = "ACTIVE_LOCK_REASON")
    var activeLockReason: String? = null,

    @Column(name = "BODY")
    var body: String = "",

    @Column(name = "PERFORMED_VIA_GITHUB_APP")
    var performedViaGithubApp: String? = null,

    @OneToOne(mappedBy = "issue")
    var payload: PayloadModel? = null
) : Serializable
