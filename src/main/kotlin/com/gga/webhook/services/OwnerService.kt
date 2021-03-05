package com.gga.webhook.services

import com.gga.webhook.models.vO.OwnerVo

interface OwnerService {

    fun getOwner(): OwnerVo?

}