package com.gga.webhook.repositories

import com.gga.webhook.models.SenderModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SenderRepository : JpaRepository<SenderModel, Long> {

    fun findByLogin(login: String): Optional<SenderModel>

    @Query("SELECT S FROM SenderModel S WHERE S.event.action = :action")
    fun findByEventAction(action: String): Optional<SenderModel>

}