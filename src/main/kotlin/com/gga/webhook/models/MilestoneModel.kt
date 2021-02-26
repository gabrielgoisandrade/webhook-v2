package com.gga.webhook.models

import java.io.Serializable
import java.time.Instant
import javax.persistence.*


@Entity
@Table(name = "MILESTONE")
data class MilestoneModel @JvmOverloads constructor(
        @Column(name = "URL")
        var url: String = "",

        @Column(name = "HTML_URL")
        var htmlUrl: String = "",

        @Column(name = "LABELS_URL")
        var labelsUrl: String = "",

        @Id
        @Column(name = "MILESTONE_ID")
        var id: Long = 0,

        @Column(name = "NODE_ID", unique = true)
        var nodeId: String = "",

        @Column(name = "NUMBER")
        var number: Long = 0,

        @Column(name = "TITLE")
        var title: String = "",

        @Column(name = "DESCRIPTION")
        var description: String? = null,

        @OneToOne(mappedBy = "milestone")
        var creator: CreatorModel? = null,

        @Column(name = "OPEN_ISSUES")
        var openIssues: Int = 0,

        @Column(name = "CLOSED_ISSUES")
        var closedIssues: Int = 0,

        @Column(name = "STATE")
        var state: String = "",

        @Column(name = "CREATED_AT")
        var createdAt: Instant? = null,

        @Column(name = "UPDATED_AT")
        var updatedAt: Instant? = null,

        @Column(name = "DUE_ON")
        var dueOn: Instant? = null,

        @Column(name = "CLOSED_AT")
        var closedAt: Instant? = null,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(
                name = "ISSUE_ID",
                referencedColumnName = "ISSUE_ID",
                foreignKey = ForeignKey(name = "C_MILESTONE_ISSUE")
        )
        var issue: IssueModel? = null
) : Serializable