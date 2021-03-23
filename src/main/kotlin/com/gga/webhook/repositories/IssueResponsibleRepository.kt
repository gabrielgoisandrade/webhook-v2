package com.gga.webhook.repositories

import com.gga.webhook.models.IssueResponsibleModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IssueResponsibleRepository : JpaRepository<IssueResponsibleModel, Long> {

    @Query("SELECT I FROM IssueResponsibleModel I WHERE I.issue.id = :issueId AND I.assignees.id = :assigneeId")
    fun findByIssueIdAndAssigneesId(issueId: Long, assigneeId: Long): Optional<IssueResponsibleModel>

}