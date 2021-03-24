package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.DuplicityHelper
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.services.AssigneesService
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
class AssigneesServiceImpl @Autowired constructor(
    private val repository: AssigneesRepository
) : AssigneesService, DuplicityHelper<AssigneesModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("assigneesByIssueNumber", allEntries = true)
    override fun saveAssignees(assignees: HashSet<AssigneesModel>): HashSet<AssigneesModel> {
        val assigneesFound: HashMap<String, HashSet<AssigneesModel>> = this.findDuplicatedValues(assignees)

        if (assigneesFound["newValues"]!!.isEmpty()) {
            this.log.info("Assignees: Returning existing Assignees.")
            return assigneesFound["existingValues"]!!
        }

        return if (assigneesFound["existingValues"]!!.isEmpty()) {
            this.log.info("Assignees: Saving new Assignees.")
            return this.repository.saveAll(assigneesFound["newValues"]!!).toHashSet()
        } else {
            this.log.info("Assignees: Saving new Assignees.")
            this.repository.saveAll(assigneesFound["newValues"]!!).toHashSet()
        }
    }

    @Cacheable("assigneesByIssueNumber")
    override fun findAssigneesByIssueNumber(issueNumber: Int): HashSet<AssigneesDto> {
        val issueFound: Optional<HashSet<AssigneesModel>> = this.repository.findByIssueNumber(issueNumber)

        return if (issueFound.isPresent)
            (issueFound.get() convertTo AssigneesDto::class.java).toHashSet()
        else
            throw RelationNotFoundException("Assignees: There isn't any Assignees related with this Issue")

    }

    override fun findDuplicatedValues(newValues: HashSet<AssigneesModel>): HashMap<String, HashSet<AssigneesModel>> {
        val existingValues: MutableList<AssigneesModel> = mutableListOf()

        newValues.forEach {
            this.repository.findByLogin(it.login).also { found: Optional<AssigneesModel> ->
                if (found.isPresent) existingValues.add(found.get())
            }
        }

        val assigneesToSave: List<AssigneesModel> = newValues.filter {
            !(existingValues convertTo AssigneesDto::class.java).contains(it convertTo AssigneesDto::class.java)
        }

        return hashMapOf("newValues" to assigneesToSave.toHashSet(), "existingValues" to existingValues.toHashSet())
    }

}
