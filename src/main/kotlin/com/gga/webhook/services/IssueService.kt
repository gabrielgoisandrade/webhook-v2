package com.gga.webhook.services

import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.dTO.IssueDto

interface IssueService {

    fun saveIssue(issue: IssueModel): IssueModel

    fun findIssueByNumber(number: Int): IssueDto

    fun findIssueByEventAction(action: String): IssueDto

}
