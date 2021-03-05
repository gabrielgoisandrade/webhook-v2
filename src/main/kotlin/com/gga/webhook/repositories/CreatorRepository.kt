package com.gga.webhook.repositories

import com.gga.webhook.models.CreatorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CreatorRepository : JpaRepository<CreatorModel, Long> {

    @Query("select c from CreatorModel c join MilestoneModel m on c.id = m.creator.id")
    fun getCreator(): CreatorModel?

}