package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AssociationEntityHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.services.AssigneesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@EnableCaching
class AssigneesServiceImpl @Autowired constructor(
    private val repository: AssigneesRepository
) : AssigneesService, AssociationEntityHelper<AssigneesModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private val helper: PageableHelper<AssigneesRepository> = PageableHelper()

    @Transactional
    @CacheEvict("assigneesByIssueNumber", allEntries = true)
    override fun saveAssignees(assignees: List<AssigneesModel>): List<AssigneesModel> {
        val assigneesFound: HashMap<String, List<AssigneesModel>> = findDuplicatedValues(assignees)

        if (assigneesFound["newValues"]!!.isEmpty()) {
            this.log.info("Assignees: Returning existing Assignees.")
            return assigneesFound["existingValues"]!!
        }

        return if (assigneesFound["existingValues"]!!.isEmpty()) {
            this.log.info("Assignees: Saving new Assignees.")
            return this.repository.saveAll(assigneesFound["newValues"]!!)
        } else {
            this.log.info("Assignees: Saving new Assignees.")
            this.repository.saveAll(assigneesFound["newValues"]!!)
        }
    }

    @Cacheable("assigneesByIssueNumber")
    override fun findAssigneesByIssueNumber(issueNumber: Int, page: Int, limit: Int, sort: String): Page<AssigneesDto> {
        val issueFound: Optional<List<AssigneesModel>> = this.repository.findByIssueNumber(issueNumber)

        return if (issueFound.isPresent)
            this.helper.getPageableContent(issueFound.get().toList(), page, limit, sort)
        else
            throw RelationNotFoundException("Assignees: There isn't any Assignees related with this Issue")
    }

    override fun findDuplicatedValues(newValues: List<AssigneesModel>): HashMap<String, List<AssigneesModel>> {
        val existingValues: List<AssigneesModel> = getExistingValues(newValues).also { it filterValues newValues }

        val assigneesToSave: List<AssigneesModel> = newValues.filter { !existingValues.contains(it) }

        return hashMapOf("newValues" to assigneesToSave, "existingValues" to existingValues)
    }

    override infix fun List<AssigneesModel>.filterValues(toFilter: List<AssigneesModel>) {
        for (assignee: Int in this.indices)
            toFilter.map { if (it.nodeId == this[assignee].nodeId) it.id = this[assignee].id }
    }

    override fun getExistingValues(newValues: List<AssigneesModel>): List<AssigneesModel> {
        val valuesFound: MutableList<AssigneesModel> = mutableListOf()

        newValues.forEach {
            this.repository.findByLogin(it.login).also { found: Optional<AssigneesModel> ->
                if (found.isPresent) valuesFound.add(found.get())
            }
        }

        return valuesFound
    }

}
