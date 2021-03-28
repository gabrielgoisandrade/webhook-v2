package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.helper.AlterationsHelper
import com.gga.webhook.models.RepositoryModel
import com.gga.webhook.models.dTO.RepositoryDto
import com.gga.webhook.repositories.RepositoryRepository
import com.gga.webhook.services.RepositoryService
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
class RepositoryServiceImpl @Autowired constructor(
    private val repository: RepositoryRepository
) : RepositoryService, AlterationsHelper<RepositoryModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("repositoryByEventAction", "repositoryByName", "repositoryByEventAction", allEntries = true)
    override fun saveRepository(repository: RepositoryModel) {
        val repositoryFound: Optional<RepositoryModel> = this.repository.findByName(repository.name)

        if (repositoryFound.isPresent) {
            this.collectAlterations(repository, repositoryFound.get())?.let {
                log.info("Repository: Saving alterations.")
                this.repository.save(it)
            } ?: log.info("Repository: No alterations found.")
        } else {
            log.info("Repository: Saving new Repository.")

            this.repository.save(repository)
        }
    }

    override fun collectAlterations(newResult: RepositoryModel, actualResult: RepositoryModel): RepositoryModel? {
        newResult.id = actualResult.id

        var updatedRepository: RepositoryModel? = null

        if (actualResult != newResult) {

            updatedRepository = newResult

            if (actualResult.event!! != newResult.event!!) {
                log.info("Repository: Alterations found.")
                log.info("Repository: Updating foreign key 'EVENT_ID'.")

                updatedRepository = actualResult.apply { this.event = newResult.event }
            }

            if (actualResult.owner != newResult.owner) {
                log.info("Repository: Alterations found.")
                log.info("Repository: Updating foreign key 'OWNER_ID'.")

                updatedRepository = actualResult.apply { this.owner = newResult.owner }
            }

            if (actualResult.license != newResult.license) {
                log.info("Repository: Alterations found.")
                log.info("Repository: Updating foreign key 'LICENSE_ID'.")

                updatedRepository = actualResult.apply { this.license = newResult.license }
            }
        }
        return updatedRepository
    }

    @Cacheable("repositoryByName")
    override fun findRepositoryByName(name: String): RepositoryDto = this.repository.findByName(name).orElseThrow {
        RepositoryNotFoundException("Repository '$name' not found.")
    } convertTo RepositoryDto::class.java

    @Cacheable("repositoryByEventAction")
    override fun findRepositoryByEventAction(action: String): RepositoryDto =
        this.repository.findByEventAction(action).orElseThrow {
            RepositoryNotFoundException("There isn't any Repository related with this Event.")
        } convertTo RepositoryDto::class.java

}
