package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AlterationsHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.MilestoneModel
import com.gga.webhook.models.dTO.MilestoneDto
import com.gga.webhook.repositories.MilestoneRepository
import com.gga.webhook.services.MilestoneService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@EnableCaching
class MilestoneServiceImpl @Autowired constructor(
    private val repository: MilestoneRepository
) : MilestoneService, AlterationsHelper<MilestoneModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("milestoneByID", "milestoneByIssueNumber", allEntries = true)
    override fun saveMilestone(milestone: MilestoneModel?): MilestoneModel? {
        if (milestone == null) {
            this.log.info("Milestone: No Milestone to save.")
            return null
        }

        val milestoneFound: Optional<MilestoneModel> = this.repository.findByNumber(milestone.number)

        return if (milestoneFound.isPresent) {
            this.collectAlterations(milestone, milestoneFound.get())?.let {
                log.info("Milestone: Saving alterations.")

                this.repository.save(it)
            } ?: milestoneFound.get()
        } else {
            this.log.info("Milestone: Saving new Milestone.")

            this.repository.save(milestone)
        }
    }

    override fun collectAlterations(newResult: MilestoneModel, actualResult: MilestoneModel): MilestoneModel? {
        newResult.id = actualResult.id

        var updatedMilestone: MilestoneModel? = null

        if (newResult != actualResult) {
            log.info("Milestone: Alterations found.")

            updatedMilestone = newResult

            if (actualResult.creator != newResult.creator) {
                log.info("Milestone: Alterations found.")
                log.info("Milestone: Updating foreign key 'CREATOR_ID'.")

                updatedMilestone = actualResult.apply { this.creator = newResult.creator }
            }
        }

        return updatedMilestone
    }

    @Cacheable("milestoneByIssueNumber")
    override fun findMilestoneByIssueNumber(issueNumber: Int): MilestoneDto =
        this.repository.findByIssueNumber(issueNumber).orElseThrow {
            RelationNotFoundException("There isn't any Milestone related with this Issue.")
        } convertTo MilestoneDto::class.java

}
