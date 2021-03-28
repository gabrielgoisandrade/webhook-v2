package com.gga.webhook.repositories

import com.gga.webhook.models.IssueModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IssueRepository : JpaRepository<IssueModel, Long> {

    fun findByNumber(number: Int): Optional<IssueModel>

    @Query("SELECT I FROM IssueModel I where I.event.action = :action")
    fun findByEventAction(action: String): Optional<IssueModel>

}