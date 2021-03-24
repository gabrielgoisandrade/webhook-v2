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
    override fun saveLabels(labels: HashSet<LabelsModel>): HashSet<LabelsModel> {
        val labelsFound: HashMap<String, HashSet<LabelsModel>> = this.findDuplicatedValues(labels)

        if (labelsFound["newValues"]!!.isEmpty()) {
            this.log.info("Labels: Returning existing Labels.")
            return labelsFound["existingValues"]!!
        }

        return if (labelsFound["existingValues"]!!.isEmpty()) {
            this.log.info("Labels: Saving new Labels.")
            return this.repository.saveAll(labelsFound["newValues"]!!).toHashSet()
        } else {
            this.log.info("Labels: Saving new Labels.")
            this.repository.saveAll(labelsFound["newValues"]!!).toHashSet()
        }
    }

    @Cacheable("labelsByIssueNumber")
    override fun findLabelsByIssueNumber(issueNumber: Int): HashSet<LabelsDto> {
        val issueFound: Optional<HashSet<LabelsModel>> = this.repository.findByIssueNumber(issueNumber)

        return if (issueFound.isPresent)
            (issueFound.get() convertTo LabelsDto::class.java).toHashSet()
        else
            throw  RelationNotFoundException("There isn't any Labels related with this Issue")
    }

    override fun findDuplicatedValues(newValues: HashSet<LabelsModel>): HashMap<String, HashSet<LabelsModel>> {
        val existingValues: MutableList<LabelsModel> = mutableListOf()

        newValues.forEach {
            this.repository.findByName(it.name).also { found: Optional<LabelsModel> ->
                if (found.isPresent) existingValues.add(found.get())
            }
        }

        val labelsToSave: List<LabelsModel> = newValues.filter {
            !(existingValues convertTo LabelsDto::class.java).contains(it convertTo LabelsDto::class.java)
        }

        return hashMapOf("newValues" to labelsToSave.toHashSet(), "existingValues" to existingValues.toHashSet())
    }

}
