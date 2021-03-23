package com.gga.webhook.repositories

import com.gga.webhook.models.OwnerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface OwnerRepository : JpaRepository<OwnerModel, Long> {

    fun findByLogin(login: String): Optional<OwnerModel>

    @Query("SELECT R.owner FROM RepositoryModel R WHERE R.name = :name")
    fun findByRepositoryName(name: String): Optional<OwnerModel>

}