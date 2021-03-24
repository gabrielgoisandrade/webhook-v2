package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "LABELS")
data class LabelsModel @JvmOverloads constructor(
    @Id
    @Column(name = "LABELS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "NODE_ID")
    var nodeId: String = "",

    @Column(name = "URL")
    var url: String = "",

    @Column(name = "NAME")
    var name: String = "",

    @Column(name = "COLOR")
    var color: String = "",

    @Column(name = "`default`")
    var default: Boolean = false,

    @Column(name = "DESCRIPTION")
    var description: String? = null
) : Serializable