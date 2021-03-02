package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneesModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AssigneesRepository : JpaRepository<AssigneesModel, Long>{

    @Query("SELECT A FROM AssigneesModel A JOIN FETCH A.issue")
    fun findAssignees(): HashSet<AssigneesModel>

}