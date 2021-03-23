package com.gga.webhook.repositories

import com.gga.webhook.models.MilestoneModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MilestoneRepository : JpaRepository<MilestoneModel, Long> {

    fun findByNumber(number: Int): Optional<MilestoneModel>

    @Query("SELECT I.milestone FROM IssueModel I WHERE I.number = :number")
    fun findByIssueNumber(number: Int): Optional<MilestoneModel>

}