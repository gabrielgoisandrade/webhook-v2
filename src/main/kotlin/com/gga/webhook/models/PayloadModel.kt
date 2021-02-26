package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PAYLOAD")
data class PayloadModel @JvmOverloads constructor(
        @Id
        @Column(name = "PAYLOAD_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0L,

        @Column(name = "ACTION")
        var action: String = "",

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(referencedColumnName = "ISSUE_ID", foreignKey = ForeignKey(name = "C_ISSUE_PAYLOAD"))
        var issue: IssueModel? = null,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(referencedColumnName = "REPOSITORY_ID", foreignKey = ForeignKey(name = "C_REPOSITORY_PAYLOAD"))
        var repository: RepositoryModel? = null,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(referencedColumnName = "SENDER_ID", foreignKey = ForeignKey(name = "C_SENDER_PAYLOAD"))
        var sender: SenderModel? = null
) : Serializable
