package com.gga.webhook.models

import org.hibernate.annotations.NaturalId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "LABELS")
data class LabelsModel @JvmOverloads constructor(
    @Id
    @Column(name = "LABELS_ID")
    var id: Long = 0L,

    @NaturalId
    @Column(name = "NODE_ID", unique = true)
    var nodeId: String,

    @Column(name = "URL")
    var url: String,

    @Column(name = "NAME")
    var name: String,

    @Column(name = "COLOR")
    var color: String,

    @Column(name = "DEFAULT")
    var default: Boolean = false,

    @Column(name = "DESCRIPTION")
    var description: String? = null,

    @ManyToOne
    @JoinColumn(name = "ISSUE_ID", referencedColumnName = "ISSUE_ID", foreignKey = ForeignKey(name = "C_LABEL_ISSUE"))
    var issue: IssueModel? = null
) : Serializable