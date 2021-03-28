package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AlterationsHelper
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.repositories.IssueRepository
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.IssueService
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
class IssueServiceImpl @Autowired constructor(
    private val issueRepository: IssueRepository,
    private val labelsRepository: LabelsRepository,
    private val assigneesRepository: AssigneesRepository
) : IssueService, AlterationsHelper<IssueModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("issueByNumber", "issueByEventAction", "issueByEventAction", allEntries = true)
    override fun saveIssue(issue: IssueModel): IssueModel {
        val issueFound: Optional<IssueModel> = this.issueRepository.findByNumber(issue.number)

        return if (issueFound.isPresent) {
            this.collectAlterations(issue, issueFound.get())?.let {
                log.info("Issue: Saving alterations.")

                this.issueRepository.save(it)
            } ?: issueFound.get().also { this.log.info("Issue: Returning existing Issue.") }
        } else {
            this.log.info("Issue: Saving new Issue.")

            this.issueRepository.save(issue)
        }
    }

    override fun collectAlterations(newResult: IssueModel, actualResult: IssueModel): IssueModel? {
        newResult.id = actualResult.id

        var updatedIssue: IssueModel? = null

        if (actualResult != newResult) {
            log.info("Issue: Alterations found.")

            updatedIssue = newResult

            if (actualResult.event != newResult.event) {
                log.info("Issue: Alterations found.")
                log.info("Issue: Updating foreign key 'EVENT_ID'.")

                updatedIssue = actualResult.apply { this.event = newResult.event }
            }

            if (actualResult.milestone != newResult.milestone) {
                log.info("Issue: Alterations found.")
                log.info("Issue: Updating foreign key 'MILESTONE_ID'.")

                updatedIssue = actualResult.apply { this.milestone = newResult.milestone }
            }

            if (actualResult.user != newResult.user) {
                log.info("Issue: Alterations found.")
                log.info("Issue: Updating foreign key 'USER_ID'.")

                updatedIssue = actualResult.apply { this.user = newResult.user }
            }

            if (actualResult.assignee != newResult.assignee) {
                log.info("Issue: Alterations found.")
                log.info("Issue: Updating foreign key 'ASSIGNEE_ID'.")

                updatedIssue = actualResult.apply { this.assignee = newResult.assignee }
            }
        }

        return updatedIssue
    }

    @Cacheable("issueByNumber")
    override fun findIssueByNumber(number: Int): IssueDto {
        val issueFound: Optional<IssueModel> = this.issueRepository.findByNumber(number)

        return if (issueFound.isPresent)
            configureObjects(issueFound.get())
        else
            throw IssueNotFoundException("Issue #$number not found.")
    }

    @Cacheable("issueByEventAction")
    override fun findIssueByEventAction(action: String): IssueDto {
        val issueFound: Optional<IssueModel> = this.issueRepository.findByEventAction(action)

        return if (issueFound.isPresent)
            configureObjects(issueFound.get())
        else
            throw RelationNotFoundException("There isn't any Issue related with this Event.")
    }

    private fun configureObjects(issue: IssueModel): IssueDto = (issue convertTo IssueDto::class.java).apply {
        this.assignees = getAssignees(issue.number)
        this.labels = getLabels(issue.number)
    }

    private fun getAssignees(number: Int): List<AssigneesDto> =
        (this.assigneesRepository.findByIssueNumber(number)
            .orElse(emptyList()) convertTo AssigneesDto::class.java)

    private fun getLabels(number: Int): List<LabelsDto> =
        (this.labelsRepository.findByIssueNumber(number)
            .orElse(emptyList()) convertTo LabelsDto::class.java)

}
