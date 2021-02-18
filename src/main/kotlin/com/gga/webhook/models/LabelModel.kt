package com.gga.webhook.models

import org.hibernate.annotations.NaturalId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "LABEL")
data class LabelModel @JvmOverloads constructor(
    @Id
    @Column(name = "LABEL_ID")
    val id: Long = 0L,

    @NaturalId
    @Column(name = "NODE_ID", unique = true)
    val nodeId: String,

    @Column(name = "URL")
    val url: String,

    @Column(name = "NAME")
    val name: String,

    @Column(name = "COLOR")
    val color: String,

    @Column(name = "DEFAULT")
    val default: Boolean = false,

    @Column(name = "DESCRIPTION")
    val description: String? = null,

    @ManyToOne
    @JoinColumn(name = "ISSUE_ID", referencedColumnName = "ISSUE_ID", foreignKey = ForeignKey(name = "C_LABEL_ISSUE"))
    val issue: IssueModel? = null
): Serializable