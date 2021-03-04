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
    @JoinColumn(name = "ISSUE_ID", foreignKey = ForeignKey(name = "C_PAYLOAD_ISSUE"))
    var issue: IssueModel? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "REPOSITORY_ID", foreignKey = ForeignKey(name = "C_PAYLOAD_REPOSITORY"))
    var repository: RepositoryModel? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", foreignKey = ForeignKey(name = "C_PAYLOAD_SENDER"))
    var sender: SenderModel? = null
) : Serializable
