package com.gga.webhook.services

import com.gga.webhook.models.MilestoneModel
import com.gga.webhook.models.dTO.MilestoneDto

interface MilestoneService {

    fun saveMilestone(milestone: MilestoneModel?): MilestoneModel?

    fun findMilestoneByIssueNumber(issueNumber: Int): MilestoneDto

}
