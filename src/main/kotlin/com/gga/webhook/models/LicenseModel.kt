package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "LICENSE")
data class LicenseModel @JvmOverloads constructor(
    @Id
    @Column(name = "LICENSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "KEY")
    var key: String = "",

    @Column(name = "NAME")
    var name: String = "",

    @Column(name = "SPDX_ID")
    var spdxId: String = "",

    @Column(name = "URL")
    var url: String = "",

    @Column(name = "NODE_ID")
    var nodeId: String = ""
) : Serializable