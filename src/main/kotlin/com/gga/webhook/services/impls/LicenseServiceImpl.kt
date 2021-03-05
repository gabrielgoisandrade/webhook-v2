package com.gga.webhook.services.impls

import com.gga.webhook.models.vO.LicenseVo
import com.gga.webhook.repositories.LicenseRepository
import com.gga.webhook.services.LicenseService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LicenseServiceImpl @Autowired constructor(
    private val licenseRepository: LicenseRepository
) : LicenseService {

    override fun getLicense(): LicenseVo? = this.licenseRepository.getLicense() convertTo LicenseVo::class.java

}