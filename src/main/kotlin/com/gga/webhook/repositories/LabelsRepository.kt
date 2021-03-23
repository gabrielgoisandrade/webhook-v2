package com.gga.webhook.repositories

import com.gga.webhook.models.LabelsModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface LabelsRepository : JpaRepository<LabelsModel, Long> {

    fun findByName(name: String): Optional<LabelsModel>

    @Query("SELECT I.labels FROM IssueClassifierModel I WHERE I.issue.number = :number")
    fun findByIssueNumber(number: Int): Optional<List<LabelsModel>>

}
