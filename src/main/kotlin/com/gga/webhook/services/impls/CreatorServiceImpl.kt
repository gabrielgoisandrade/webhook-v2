package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.dTO.CreatorDto
import com.gga.webhook.repositories.CreatorRepository
import com.gga.webhook.services.CreatorService
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
class CreatorServiceImpl @Autowired constructor(
    private val repository: CreatorRepository
) : CreatorService {

    private val helper: PageableHelper<CreatorModel> = PageableHelper(this.repository)

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("creatorByID", "creatorByLogin", "creatorByMilestoneNumber", allEntries = true)
    override fun saveCreator(creator: CreatorModel?): CreatorModel? {
        if (creator == null) return null

        val creatorFound: Optional<CreatorModel> = this.repository.findByLogin(creator.login)

        return if (creatorFound.isPresent) {
            this.log.info("Creator: Returning existing Creator.")

            creatorFound.get()
        } else {
            this.log.info("Creator: Saving new Creator.")

            this.repository.save(creator)
        }
    }

    @Cacheable("creatorByMilestoneNumber")
    override fun findCreatorByMilestoneNumber(milestoneNumber: Int): CreatorDto =
        this.repository.findByMilestoneNumber(milestoneNumber).orElseThrow {
            RelationNotFoundException("There isn't any Creator related with this Milestone.")
        } convertTo CreatorDto::class.java

}
