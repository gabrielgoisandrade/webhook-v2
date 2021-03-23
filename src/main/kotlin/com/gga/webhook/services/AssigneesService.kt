package com.gga.webhook.services

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto

interface AssigneesService {

    fun saveAssignees(assignees: List<AssigneesModel>): List<AssigneesModel>

    fun findAssigneesByIssueNumber(issueNumber: Int): List<AssigneesDto>

}