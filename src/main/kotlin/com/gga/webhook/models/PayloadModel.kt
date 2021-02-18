package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*
import javax.persistence.CascadeType.ALL

@Entity
@Table(name = "PAYLOAD")
data class PayloadModel @JvmOverloads constructor(
    @Id
    @Column(name = "PAYLOAD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "ACTION")
    val action: String,

    @OneToOne(cascade = [ALL])
    @JoinColumn(name = "ISSUE_ID", referencedColumnName = "ISSUE_ID", foreignKey = ForeignKey(name = "C_ISSUE_PAYLOAD"))
    val issue: IssueModel,

    @OneToOne(cascade = [ALL])
    @JoinColumn(
        name = "REPOSITORY_ID",
        referencedColumnName = "REPOSITORY_ID",
        foreignKey = ForeignKey(name = "C_REPOSITORY_PAYLOAD")
    )
    val repository: RepositoryModel,

    @OneToOne(cascade = [ALL])
    @JoinColumn(
        name = "SENDER_ID",
        referencedColumnName = "SENDER_ID",
        foreignKey = ForeignKey(name = "C_SENDER_PAYLOAD")
    )
    val sender: SenderModel
) : Serializable
