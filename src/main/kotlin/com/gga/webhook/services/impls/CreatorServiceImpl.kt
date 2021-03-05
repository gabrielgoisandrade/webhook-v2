package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.CreatorNotFoundException
import com.gga.webhook.models.vO.CreatorVo
import com.gga.webhook.repositories.CreatorRepository
import com.gga.webhook.services.CreatorService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreatorServiceImpl @Autowired constructor(private val creatorRepository: CreatorRepository) : CreatorService {

    override fun getCreator(): CreatorVo = (this.creatorRepository.getCreator()
        ?: throw CreatorNotFoundException("No sender found.")) convertTo CreatorVo::class.java

}