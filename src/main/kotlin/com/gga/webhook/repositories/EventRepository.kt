package com.gga.webhook.repositories

import com.gga.webhook.models.EventModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<EventModel, Long> {

    fun findByAction(action: String): Optional<EventModel>

}