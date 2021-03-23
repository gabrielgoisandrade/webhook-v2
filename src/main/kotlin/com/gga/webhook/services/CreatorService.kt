package com.gga.webhook.services

import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.dTO.CreatorDto

interface CreatorService {

    fun saveCreator(creator: CreatorModel?): CreatorModel?

    fun findCreatorByMilestoneNumber(milestoneNumber: Int): CreatorDto

}
