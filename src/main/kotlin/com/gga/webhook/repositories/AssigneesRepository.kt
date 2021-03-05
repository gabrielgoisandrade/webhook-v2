package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneesModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AssigneesRepository : JpaRepository<AssigneesModel, Long> {

    @Query("select a from AssigneesModel a join IssueModel i on i.id = a.issue.id")
    fun getAssignees(): HashSet<AssigneesModel>

}