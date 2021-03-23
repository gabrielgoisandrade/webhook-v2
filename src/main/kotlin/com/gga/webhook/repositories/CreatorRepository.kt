package com.gga.webhook.repositories

import com.gga.webhook.models.CreatorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CreatorRepository : JpaRepository<CreatorModel, Long> {

    fun findByLogin(login: String): Optional<CreatorModel>

    @Query("SELECT M.creator FROM MilestoneModel M WHERE M.number = :number")
    fun findByMilestoneNumber(number: Int): Optional<CreatorModel>

}