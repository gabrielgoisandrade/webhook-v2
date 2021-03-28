package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AlterationsHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.dTO.LicenseDto
import com.gga.webhook.repositories.LicenseRepository
import com.gga.webhook.services.LicenseService
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
class LicenseServiceImpl @Autowired constructor(
    private val repository: LicenseRepository
) : LicenseService, AlterationsHelper<LicenseModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("licenseByID", "licenseByRepositoryName", allEntries = true)
    override fun saveLicense(license: LicenseModel?): LicenseModel? {
        if (license == null) {
            this.log.info("License: No License to save.")

            return null
        }

        val licenseFound: Optional<LicenseModel> = this.repository.findByKey(license.key)

        return if (licenseFound.isPresent) {
            this.collectAlterations(license, licenseFound.get())?.let {
                log.info("License: Saving alterations.")
                this.repository.save(it)
            } ?: log.info("License: No alterations found.")

            this.log.info("License: Returning existing License.")

            licenseFound.get()
        } else {
            this.log.info("License: Saving new License.")

            this.repository.save(license)
        }
    }

    @Cacheable("licenseByRepositoryName")
    override fun findLicenseByRepositoryName(repositoryName: String): LicenseDto =
        this.repository.findByRepositoryName(repositoryName).orElseThrow {
            RelationNotFoundException("There isn't any License related with this Repository.")
        } convertTo LicenseDto::class.java

    override fun collectAlterations(newResult: LicenseModel, actualResult: LicenseModel): LicenseModel? {
        newResult.id = actualResult.id

        return if (newResult != actualResult) {
            log.info("License: Alterations found.")
            newResult
        } else {
            null
        }
    }

}