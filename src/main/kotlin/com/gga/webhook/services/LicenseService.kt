package com.gga.webhook.services

import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.dTO.LicenseDto

interface LicenseService {

    fun saveLicense(license: LicenseModel?): LicenseModel?

    fun findLicenseByRepositoryName(repositoryName: String): LicenseDto

}
