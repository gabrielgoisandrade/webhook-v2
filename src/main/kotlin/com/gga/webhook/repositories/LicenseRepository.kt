package com.gga.webhook.repositories

import com.gga.webhook.models.LicenseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LicenseRepository : JpaRepository<LicenseModel, Long> {

    @Query("select l from LicenseModel l join RepositoryModel r on l.id = r.license.id")
    fun getLicense(): LicenseModel?

}