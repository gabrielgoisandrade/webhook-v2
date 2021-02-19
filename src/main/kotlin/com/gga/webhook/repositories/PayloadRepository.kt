package com.gga.webhook.repositories

import com.gga.webhook.models.PayloadModel
import org.springframework.data.jpa.repository.JpaRepository

interface PayloadRepository: JpaRepository<PayloadModel, Long>