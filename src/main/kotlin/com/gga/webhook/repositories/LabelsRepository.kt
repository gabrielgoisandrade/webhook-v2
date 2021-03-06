package com.gga.webhook.repositories

import com.gga.webhook.models.LabelsModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LabelsRepository : JpaRepository<LabelsModel, Long> {

    @Query("select l from LabelsModel l join IssueModel i on i.id = l.issue.id order by l.id desc")
    fun getLabels(): HashSet<LabelsModel>

}
