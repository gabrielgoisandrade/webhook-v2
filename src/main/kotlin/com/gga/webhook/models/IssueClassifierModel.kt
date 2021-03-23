package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ISSUE_CLASSIFIER")
data class IssueClassifierModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISSUE_CLASSIFIER_ID")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUE_ID", foreignKey = ForeignKey(name = "C_ISSUE_CLASSIFIER_ISSUE"))
    var issue: IssueModel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEES_ID", foreignKey = ForeignKey(name = "C_ISSUE_CLASSIFIER_LABELS"))
    var labels: LabelsModel? = null
) : Serializable
