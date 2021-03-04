package com.gga.webhook.services

import com.gga.webhook.models.dTO.IssueDto

interface IssueService {

    fun getIssueByNumber(number: Int): HashSet<IssueDto>

}