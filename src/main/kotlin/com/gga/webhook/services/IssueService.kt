package com.gga.webhook.services

import com.gga.webhook.models.vO.IssueVo

interface IssueService {

    fun getIssueByNumber(number: Int): HashSet<IssueVo>

    fun getIssue(): IssueVo

}