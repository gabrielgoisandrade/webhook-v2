package com.gga.webhook.services

import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.repositories.IssueRepository
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service

@Service
@EnableCaching
class IssueServiceImpl @Autowired constructor(
    private val issueRepository: IssueRepository,
    private val assigneesRepository: AssigneesRepository,
    private val labelsRepository: LabelsRepository,
) : IssueService {

    @Cacheable(cacheNames = ["issueByNumber"])
    override fun getIssueByNumber(number: Int): HashSet<IssueDto> {
        val issue: Set<IssueModel> = issueRepository.findIssueModelByNumber(number)

        if (issue.isEmpty()) throw IssueNotFoundException("Issue #$number not found.")

        return (issue convertTo (IssueDto::class.java)).map {
            it.apply {
                this.assignees = assigneesRepository.findAssignees() convertTo AssigneesDto::class.java
                this.labels = labelsRepository.findLabels() convertTo LabelsDto::class.java
            }
        }.toHashSet()
    }

}
