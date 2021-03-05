package com.gga.webhook.repositories

import com.gga.webhook.models.MilestoneModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MilestoneRepository : JpaRepository<MilestoneModel, Long> {

    @Query("select m from MilestoneModel m join IssueModel i on m.id = i.milestone.id")
    fun getMilestone(): MilestoneModel?

}