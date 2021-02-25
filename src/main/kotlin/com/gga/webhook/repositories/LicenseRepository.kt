package com.gga.webhook.repositories

import com.gga.webhook.models.LicenseModel
import org.springframework.data.jpa.repository.JpaRepository

interface LicenseRepository : JpaRepository<LicenseModel, Long>