package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ISSUE_RESPONSIBLE")
data class IssueResponsibleModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISSUE_RESPONSIBLE_ID")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUE_ID", foreignKey = ForeignKey(name = "C_ISSUE_RESPONSIBLE_ISSUE"))
    var issue: IssueModel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEES_ID", foreignKey = ForeignKey(name = "C_ISSUE_RESPONSIBLE_ASSIGNEES"))
    var assignees: AssigneesModel? = null
) : Serializable
