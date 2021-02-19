package com.gga.webhook.repositories

import com.gga.webhook.models.*
import org.springframework.data.jpa.repository.JpaRepository

interface LicenseRepository: JpaRepository<LicenseModel, Long>