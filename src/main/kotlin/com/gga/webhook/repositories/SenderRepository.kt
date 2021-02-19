package com.gga.webhook.repositories

import com.gga.webhook.models.SenderModel
import org.springframework.data.jpa.repository.JpaRepository

interface SenderRepository : JpaRepository<SenderModel, Long>