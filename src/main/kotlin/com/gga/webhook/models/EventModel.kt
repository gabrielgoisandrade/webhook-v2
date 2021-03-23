package com.gga.webhook.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "EVENT")
data class EventModel @JvmOverloads constructor(
    @Id
    @Column(name = "EVENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "ACTION", unique = true)
    var action: String = ""
) : Serializable
