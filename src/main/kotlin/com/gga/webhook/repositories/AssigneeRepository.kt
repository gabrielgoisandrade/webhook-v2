package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneeModel
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface AssigneeRepository: JpaRepository<AssigneeModel, Long>