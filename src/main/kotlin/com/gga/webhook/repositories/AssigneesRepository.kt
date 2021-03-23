package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneesModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AssigneesRepository : JpaRepository<AssigneesModel, Long> {

    fun findByLogin(login: String): Optional<AssigneesModel>

    @Query("SELECT R.assignees FROM IssueResponsibleModel R WHERE R.issue.number = :number")
    fun findByIssueNumber(number: Int): Optional<List<AssigneesModel>>

}