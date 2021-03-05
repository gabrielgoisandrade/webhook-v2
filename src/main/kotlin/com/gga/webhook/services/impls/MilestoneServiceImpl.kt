package com.gga.webhook.services.impls

import com.gga.webhook.models.vO.MilestoneVo
import com.gga.webhook.repositories.MilestoneRepository
import com.gga.webhook.services.MilestoneService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MilestoneServiceImpl @Autowired constructor(
    private val milestoneRepository: MilestoneRepository
) : MilestoneService {

    override fun getMilestone(): MilestoneVo? =
        this.milestoneRepository.getMilestone() convertTo MilestoneVo::class.java

}