package com.gga.webhook.repositories

import com.gga.webhook.models.IssueClassifierModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface IssueClassifierRepository : JpaRepository<IssueClassifierModel, Long> {

    @Query("SELECT I FROM IssueClassifierModel I WHERE I.issue.id = :issueId AND I.labels.id = :labelsId")
    fun findByIssueIdAndLabelsId(
        @Param("issueId") issueId: Long,
        @Param("labelsId") labelId: Long
    ): Optional<IssueClassifierModel>

}