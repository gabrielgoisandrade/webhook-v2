package com.gga.webhook.repositories

import com.gga.webhook.models.SenderModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SenderRepository : JpaRepository<SenderModel, Long> {

    fun findByLogin(login: String): Optional<SenderModel>

}