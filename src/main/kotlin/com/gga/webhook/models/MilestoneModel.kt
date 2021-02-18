package com.gga.webhook.models

import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "MILESTONE")
data class MilestoneModel @JvmOverloads constructor(
    @Column(name = "URL")
    val url: String,

    @Column(name = "HTML_URL")
    val htmlUrl: String,

    @Column(name = "LABELS_URL")
    val labelsUrl: String,

    @Id
    @Column(name = "MILESTONE_ID")
    val id: Long = 0,

    @Column(name = "NODE_ID", unique = true)
    val nodeId: String,

    @Column(name = "NUMBER")
    val number: Long = 0,

    @Column(name = "TITLE")
    val title: String,

    @Column(name = "DESCRIPTION")
    val description: String? = null,

    @OneToOne(mappedBy = "milestone")
    val creator: CreatorModel? = null,

    @Column(name = "OPEN_ISSUES")
    val openIssues: Int,

    @Column(name = "CLOSED_ISSUES")
    val closedIssues: Int,

    @Column(name = "STATE")
    val state: String,

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    val createdAt: Date,

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    val updatedAt: Date? = null,

    @Column(name = "DUE_ON")
    @Temporal(TemporalType.DATE)
    val dueOn: Date? = null,

    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.DATE)
    val closedAt: Date? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "ISSUE_ID",
        referencedColumnName = "ISSUE_ID",
        foreignKey = ForeignKey(name = "C_MILESTONE_ISSUE")
    )
    val issue: IssueModel? = null
) : Serializable