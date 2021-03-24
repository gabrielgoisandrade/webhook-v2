package com.gga.webhook.services

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel

interface IssueResponsibleService {

    fun saveIssueResponsible(issue: IssueModel, assignees: HashSet<AssigneesModel>)

}