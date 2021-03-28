package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AlterationsHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.OwnerModel
import com.gga.webhook.models.dTO.OwnerDto
import com.gga.webhook.repositories.OwnerRepository
import com.gga.webhook.services.OwnerService
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
class OwnerServiceImpl @Autowired constructor(
    private val repository: OwnerRepository
) : OwnerService, AlterationsHelper<OwnerModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("ownerByID", "ownerByRepositoryName", allEntries = true)
    override fun saveOwner(owner: OwnerModel): OwnerModel {
        val ownerFound: Optional<OwnerModel> = this.repository.findByLogin(owner.login)

        return if (ownerFound.isPresent) {
            this.collectAlterations(owner, ownerFound.get())?.let {
                log.info("Owner: Saving alterations.")
                this.repository.save(it)
            } ?: log.info("Owner: No alterations found.")

            this.log.info("Owner: Returning existing Owner.")

            ownerFound.get()
        } else {
            this.log.info("Owner: Saving new Owner.")

            this.repository.save(owner)
        }
    }

    @Cacheable("ownerByRepositoryName")
    override fun findOwnerByRepositoryName(repositoryName: String): OwnerDto =
        this.repository.findByRepositoryName(repositoryName).orElseThrow {
            RelationNotFoundException("There isn't any Owner related with this Repository.")
        } convertTo OwnerDto::class.java

    override fun collectAlterations(newResult: OwnerModel, actualResult: OwnerModel): OwnerModel? {
        newResult.id = actualResult.id

        return if (newResult != actualResult) {
            log.info("Owner: Alterations found.")
            newResult
        } else {
            null
        }
    }

}