package com.gga.webhook.repositories

import com.gga.webhook.models.RepositoryModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RepositoryRepository : JpaRepository<RepositoryModel, Long> {

    @Query("select r from RepositoryModel r join PayloadModel p on r.id = p.repository.id")
    fun getRepository(): RepositoryModel?

}