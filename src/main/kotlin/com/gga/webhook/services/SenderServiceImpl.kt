package com.gga.webhook.services

import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.repositories.SenderRepository
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SenderServiceImpl @Autowired constructor(private val senderRepository: SenderRepository) : SenderService {

    override fun getSender(): SenderVo {
        return (senderRepository.getSender()
            ?: throw SenderNotFoundException("No senders found.")) convertTo SenderVo::class.java
    }

}