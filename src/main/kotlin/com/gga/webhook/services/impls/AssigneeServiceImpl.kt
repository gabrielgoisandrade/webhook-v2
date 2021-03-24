package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.AssigneeModel
import com.gga.webhook.models.dTO.AssigneeDto
import com.gga.webhook.repositories.AssigneeRepository
import com.gga.webhook.services.AssigneeService
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
class AssigneeServiceImpl @Autowired constructor(
    private val repository: AssigneeRepository
) : AssigneeService {

    private val helper: PageableHelper<AssigneeModel> = PageableHelper(this.repository)

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("assigneeByID", "assigneeByIssueNumber", "assigneeByLogin", "assigneeByIssueId", allEntries = true)
    override fun saveAssignee(assignee: AssigneeModel): AssigneeModel {
        val assigneeFound: Optional<AssigneeModel> = this.repository.findByLogin(assignee.login)

        return if (assigneeFound.isPresent) {
            this.log.info("Assignee: Returning existing Assignee.")

            assigneeFound.get()
        } else {
            this.log.info("Assignee: Saving new Assignee.")

            this.repository.save(assignee)
        }
    }

    @Cacheable("assigneeByIssueNumber")
    override fun findAssigneeByIssueNumber(issueNumber: Int): AssigneeDto =
        this.repository.findByIssueNumber(issueNumber).orElseThrow {
            RelationNotFoundException("There isn't any Assignee related with this Issue.")
        } convertTo AssigneeDto::class.java

}
