package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.DuplicityHelper
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.LabelsService
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
class LabelsServiceImpl @Autowired constructor(
    private val repository: LabelsRepository
) : LabelsService, DuplicityHelper<LabelsModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("labelsByIssueNumber", allEntries = true)
    override fun saveLabels(labels: List<LabelsModel>): List<LabelsModel> {
        if (labels.isEmpty()) {
            this.log.info("Labels: No Labels to save.")
            return emptyList()
        }

        val labelsFound: HashMap<String, List<LabelsModel>> = this.findDuplicatedValues(labels)

        if (labelsFound["newValues"]!!.isEmpty()) {
            this.log.info("Labels: Returning existing Labels.")
            return labelsFound["existingValues"]!!
        }

        return if (labelsFound["existingValues"]!!.isEmpty()) {
            this.log.info("Labels: Saving new Labels.")
            return this.repository.saveAll(labels convertTo LabelsModel::class.java)
        } else {
            this.log.info("Labels: Saving new Labels.")
            this.repository.saveAll(labelsFound["newValues"]!!)
        }
    }

    @Cacheable("labelsByIssueNumber")
    override fun findLabelsByIssueNumber(issueNumber: Int): List<LabelsDto> =
        this.repository.findByIssueNumber(issueNumber).orElseThrow {
            RelationNotFoundException("There isn't any Labels related with this Issue")
        } convertTo LabelsDto::class.java

    override fun findDuplicatedValues(newValues: List<LabelsModel>): HashMap<String, List<LabelsModel>> {
        val existingValues: MutableList<LabelsModel> = mutableListOf()

        newValues.forEach {
            this.repository.findByName(it.name).also { found: Optional<LabelsModel> ->
                if (found.isPresent) existingValues.add(found.get())
            }
        }

        val labelsToSave: List<LabelsModel> = newValues.filter { !existingValues.contains(it) }

        return hashMapOf("newValues" to labelsToSave, "existingValues" to existingValues)
    }

}
