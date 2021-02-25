package com.gga.webhook.repositories

import com.gga.webhook.models.CreatorModel
import org.springframework.data.jpa.repository.JpaRepository

interface CreatorRepository : JpaRepository<CreatorModel, Long>