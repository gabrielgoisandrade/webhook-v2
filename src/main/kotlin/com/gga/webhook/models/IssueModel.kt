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
    val url: String,

    @Column(name = "REPOSITORY_URL")
    val repositoryUrl: String,

    @Column(name = "LABELS_URL")
    val labelsUrl: String,

    @Column(name = "COMMENTS_URL")
    val commentsUrl: String,

    @Column(name = "EVENTS_URL")
    val eventsUrl: String,

    @Column(name = "HTML_URL")
    val htmlUrl: String,

    @Id
    @Column(name = "ISSUE_ID")
    val id: Long = 0,

    @NaturalId
    @Column(name = "NODE_ID", unique = true)
    val nodeId: String,

    @Column(name = "NUMBER")
    val number: Int = 0,

    @Column(name = "TITLE")
    val title: String,

    @OneToOne(mappedBy = "issue")
    val user: UserModel,

    @OneToMany(mappedBy = "issue")
    val labels: Set<LabelModel> = hashSetOf(),

    @Column(name = "STATE")
    val state: String,

    @Column(name = "LOCKED")
    val locked: Boolean = false,

    @OneToOne(mappedBy = "issue")
    val assignee: AssigneeModel? = null,

    @OneToMany(mappedBy = "issue")
    val assignees: Set<AssigneesModel>? = hashSetOf(),

    @OneToOne(mappedBy = "issue")
    val milestone: MilestoneModel? = null,

    @Column(name = "COMMENTS")
    val comments: Int = 0,

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    val createdAt: Date,

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    val updatedAt: Date? = null,

    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.DATE)
    val closedAt: Date? = null,

    @Column(name = "AUTOR_ASSOCIATION")
    val authorAssociation: String,

    @Column(name = "ACTIVE_LOCK_REASON")
    val activeLockReason: String? = null,

    @Column(name = "BODY")
    val body: String = "",

    @Column(name = "PERFORMED_VIA_GITHUB_APP")
    val performedViaGithubApp: String? = null,

    @OneToOne(mappedBy = "issue")
    val payload: PayloadModel? = null
) : Serializable
