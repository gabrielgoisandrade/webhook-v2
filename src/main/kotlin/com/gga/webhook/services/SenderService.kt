package com.gga.webhook.services

import com.gga.webhook.models.vO.SenderVo

interface SenderService {

    fun getSender(): SenderVo

}