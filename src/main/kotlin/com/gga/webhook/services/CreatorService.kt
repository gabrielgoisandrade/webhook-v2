package com.gga.webhook.services

import com.gga.webhook.models.vO.CreatorVo

interface CreatorService {

    fun getCreator(): CreatorVo?

}