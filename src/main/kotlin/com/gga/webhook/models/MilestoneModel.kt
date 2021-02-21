package com.gga.webhook.models

import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "MILESTONE")
data class MilestoneModel @JvmOverloads constructor(
    @Column(name = "URL")
    var url: String,

    @Column(name = "HTML_URL")
    var htmlUrl: String,

    @Column(name = "LABELS_URL")
    var labelsUrl: String,

    @Id
    @Column(name = "MILESTONE_ID")
    val id: Long = 0,

    @Column(name = "NODE_ID", unique = true)
    var nodeId: String,

    @Column(name = "NUMBER")
    var number: Long = 0,

    @Column(name = "TITLE")
    var title: String,

    @Column(name = "DESCRIPTION")
    var description: String? = null,

    @OneToOne(mappedBy = "milestone")
    var creator: CreatorModel? = null,

    @Column(name = "OPEN_ISSUES")
    var openIssues: Int,

    @Column(name = "CLOSED_ISSUES")
    var closedIssues: Int,

    @Column(name = "STATE")
    var state: String,

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    var createdAt: Date,

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    var updatedAt: Date? = null,

    @Column(name = "DUE_ON")
    @Temporal(TemporalType.DATE)
    var dueOn: Date? = null,

    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.DATE)
    var closedAt: Date? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "ISSUE_ID",
        referencedColumnName = "ISSUE_ID",
        foreignKey = ForeignKey(name = "C_MILESTONE_ISSUE")
    )
    var issue: IssueModel? = null
) : Serializable