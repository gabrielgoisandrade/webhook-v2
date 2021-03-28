package com.gga.webhook.services

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto
import org.springframework.data.domain.Page

interface AssigneesService {

    fun saveAssignees(assignees: List<AssigneesModel>): List<AssigneesModel>

    fun findAssigneesByIssueNumber(issueNumber: Int, page: Int, limit: Int, sort: String): Page<AssigneesDto>

}