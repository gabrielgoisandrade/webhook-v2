package com.gga.webhook.repositories

import com.gga.webhook.models.IssueModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
import kotlin.collections.HashSet

interface IssueRepository : JpaRepository<IssueModel, Long> {

    fun findByNumber(number: Int): Optional<IssueModel>

    @Query("SELECT R.issue FROM IssueClassifierModel R where R.issue.id = :id")
    fun findByAssigneeId(id: Long): HashSet<IssueModel>

}