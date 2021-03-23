package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneeModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AssigneeRepository : JpaRepository<AssigneeModel, Long> {

    fun findByLogin(login: String): Optional<AssigneeModel>

    @Query("SELECT I.assignee FROM IssueModel I WHERE I.number = :number")
    fun findByIssueNumber(number: Int): Optional<AssigneeModel>

}