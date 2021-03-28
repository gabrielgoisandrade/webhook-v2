package com.gga.webhook.repositories

import com.gga.webhook.models.RepositoryModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RepositoryRepository : JpaRepository<RepositoryModel, Long> {

    fun findByName(name: String): Optional<RepositoryModel>

    @Query("SELECT R FROM RepositoryModel R WHERE R.event.action = :action")
    fun findByEventAction(action: String): Optional<RepositoryModel>

}