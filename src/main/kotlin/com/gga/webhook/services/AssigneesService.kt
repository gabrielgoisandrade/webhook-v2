package com.gga.webhook.services

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto

interface AssigneesService {

    fun saveAssignees(assignees: HashSet<AssigneesModel>): HashSet<AssigneesModel>

    fun findAssigneesByIssueNumber(issueNumber: Int): HashSet<AssigneesDto>

}