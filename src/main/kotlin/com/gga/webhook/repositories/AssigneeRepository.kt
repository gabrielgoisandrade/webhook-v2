package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneeModel
import org.springframework.data.jpa.repository.JpaRepository

interface AssigneeRepository : JpaRepository<AssigneeModel, Long>