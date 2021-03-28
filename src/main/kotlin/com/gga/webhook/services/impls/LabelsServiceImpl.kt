package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AssociationEntityHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.LabelsService
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
class LabelsServiceImpl @Autowired constructor(
    private val repository: LabelsRepository
) : LabelsService, AssociationEntityHelper<LabelsModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private val helper: PageableHelper<LabelsRepository> = PageableHelper()

    @Transactional
    @CacheEvict("labelsByIssueNumber", allEntries = true)
    override fun saveLabels(labels: List<LabelsModel>): List<LabelsModel> {
        val labelsFound: HashMap<String, List<LabelsModel>> = findDuplicatedValues(labels)

        if (labelsFound["newValues"]!!.isEmpty()) {
            this.log.info("Labels: Returning existing Labels.")
            return labelsFound["existingValues"]!!
        }

        return if (labelsFound["existingValues"]!!.isEmpty()) {
            this.log.info("Labels: Saving new Labels.")
            return this.repository.saveAll(labelsFound["newValues"]!!)
        } else {
            this.log.info("Labels: Saving new Labels.")
            this.repository.saveAll(labelsFound["newValues"]!!)
        }
    }

    override fun findDuplicatedValues(newValues: List<LabelsModel>): HashMap<String, List<LabelsModel>> {
        val existingValues: List<LabelsModel> = getExistingValues(newValues).also { it filterValues newValues }

        val labelsToSave: List<LabelsModel> = newValues.filter { !existingValues.contains(it) }

        return hashMapOf("newValues" to labelsToSave, "existingValues" to existingValues)
    }

    override infix fun List<LabelsModel>.filterValues(toFilter: List<LabelsModel>) {
        for (assignee: Int in this.indices)
            toFilter.map { if (it.nodeId == this[assignee].nodeId) it.id = this[assignee].id }
    }

    override fun getExistingValues(newValues: List<LabelsModel>): List<LabelsModel> {
        val valuesFound: MutableList<LabelsModel> = mutableListOf()

        newValues.forEach {
            this.repository.findByName(it.name).also { found: Optional<LabelsModel> ->
                if (found.isPresent) valuesFound.add(found.get())
            }
        }

        return valuesFound
    }

    @Cacheable("labelsByIssueNumber")
    override fun findLabelsByIssueNumber(issueNumber: Int, page: Int, limit: Int, sort: String): Page<LabelsDto> {
        val issueFound: Optional<List<LabelsModel>> = this.repository.findByIssueNumber(issueNumber)

        return if (issueFound.isPresent)
            this.helper.getPageableContent(issueFound.get().toList(), page, limit, sort)
        else
            throw  RelationNotFoundException("There isn't any Labels related with this Issue")
    }

}
