package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.models.vO.RepositoryVo
import com.gga.webhook.repositories.RepositoryRepository
import com.gga.webhook.services.RepositoryService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RepositoryServiceImpl @Autowired constructor(
    private val repositoryRepository: RepositoryRepository
) : RepositoryService {

    override fun getRepository(): RepositoryVo = (this.repositoryRepository.getRepository()
        ?: throw RepositoryNotFoundException("No repository found.")) convertTo RepositoryVo::class.java

}