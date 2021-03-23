package com.gga.webhook.services

import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dTO.SenderDto

interface SenderService {

    fun saveSender(sender: SenderModel)

    fun findSenderByLogin(login: String): SenderDto

}