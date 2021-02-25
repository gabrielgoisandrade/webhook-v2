package com.gga.webhook.repositories

import com.gga.webhook.models.MilestoneModel
import org.springframework.data.jpa.repository.JpaRepository

interface MilestoneRepository : JpaRepository<MilestoneModel, Long>