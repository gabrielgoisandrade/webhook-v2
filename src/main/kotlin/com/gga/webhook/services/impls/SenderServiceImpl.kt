package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.repositories.SenderRepository
import com.gga.webhook.services.SenderService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SenderServiceImpl @Autowired constructor(private val senderRepository: SenderRepository) : SenderService {

    override fun getSender(): SenderVo = (this.senderRepository.getSender()
        ?: throw SenderNotFoundException("No sender found.")) convertTo SenderVo::class.java

}