package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "LICENSE")
data class LicenseModel @JvmOverloads constructor(
    @Id
    @Column(name = "LICENSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "KEY")
    val key: String,

    @Column(name = "NAME")
    val name: String,

    @Column(name = "SPDX_ID")
    val spdxId: String,

    @Column(name = "URL")
    val url: String,

    @Column(name = "NODE_ID")
    val nodeId: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "REPOSITORY_ID",
        referencedColumnName = "REPOSITORY_ID",
        foreignKey = ForeignKey(name = "C_LICENSE_REPOSITORY")
    )
    val repository: RepositoryModel? = null
) : Serializable