package com.gga.webhook.services

import com.gga.webhook.models.AssigneeModel
import com.gga.webhook.models.dTO.AssigneeDto

interface AssigneeService {

    fun saveAssignee(assignee: AssigneeModel): AssigneeModel

    fun findAssigneeByIssueNumber(issueNumber: Int): AssigneeDto

}
