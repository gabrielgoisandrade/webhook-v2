package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.vO.IssueVo
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.repositories.IssueRepository
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.IssueService
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
    private val labelsRepository: LabelsRepository
) : IssueService {

    @Cacheable(cacheNames = ["issueByNumber"])
    override fun getIssueByNumber(number: Int): HashSet<IssueVo> {
        val issue: Set<IssueModel> = issueRepository.findIssueModelByNumber(number)

        if (issue.isEmpty()) throw IssueNotFoundException("Issue #$number not found.")

        return (issue convertTo (IssueVo::class.java)).toHashSet()
    }

    override fun getIssue(): IssueVo {
        val issueModel: IssueModel = issueRepository.getIssue() ?: throw IssueNotFoundException("No issue found.")

        return (issueModel convertTo IssueVo::class.java)
    }

}
