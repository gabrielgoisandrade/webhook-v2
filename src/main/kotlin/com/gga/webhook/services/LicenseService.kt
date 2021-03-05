package com.gga.webhook.services

import com.gga.webhook.models.vO.LicenseVo

interface LicenseService {

    fun getLicense(): LicenseVo?

}