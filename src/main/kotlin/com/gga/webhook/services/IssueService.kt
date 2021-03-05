package com.gga.webhook.services

import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.vO.IssueVo

interface IssueService {

    fun getIssueByNumber(number: Int): HashSet<IssueDto>

    fun getIssue(): IssueVo

}