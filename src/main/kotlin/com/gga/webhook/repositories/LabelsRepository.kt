package com.gga.webhook.repositories

import com.gga.webhook.models.LabelsModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LabelsRepository : JpaRepository<LabelsModel, Long> {

    @Query("SELECT L FROM LabelsModel L JOIN FETCH L.issue")
    fun findLabels(): HashSet<LabelsModel>

}
