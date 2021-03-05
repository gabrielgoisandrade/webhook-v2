package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.OwnerNotFoundException
import com.gga.webhook.models.vO.OwnerVo
import com.gga.webhook.repositories.OwnerRepository
import com.gga.webhook.services.OwnerService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl @Autowired constructor(private val ownerRepository: OwnerRepository) : OwnerService {

    override fun getOwner(): OwnerVo = (this.ownerRepository.getOwner()
        ?: throw OwnerNotFoundException("No owner found.")) convertTo OwnerVo::class.java

}