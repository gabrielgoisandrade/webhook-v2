package com.gga.webhook.services

import com.gga.webhook.models.vO.MilestoneVo

interface MilestoneService {

    fun getMilestone(): MilestoneVo?

}