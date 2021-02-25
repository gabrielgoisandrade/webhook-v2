package com.gga.webhook.repositories

import com.gga.webhook.models.LabelsModel
import org.springframework.data.jpa.repository.JpaRepository

interface LabelRepository : JpaRepository<LabelsModel, Long>