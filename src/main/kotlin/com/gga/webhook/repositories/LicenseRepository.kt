package com.gga.webhook.repositories

import com.gga.webhook.models.LicenseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface LicenseRepository : JpaRepository<LicenseModel, Long> {

    fun findByKey(key: String): Optional<LicenseModel>

    @Query("SELECT R.license FROM RepositoryModel R where R.name = :name")
    fun findByRepositoryName(name: String): Optional<LicenseModel>

}