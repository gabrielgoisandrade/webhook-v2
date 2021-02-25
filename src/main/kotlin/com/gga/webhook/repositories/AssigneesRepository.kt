package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneesModel
import org.springframework.data.jpa.repository.JpaRepository

interface AssigneesRepository : JpaRepository<AssigneesModel, Long>