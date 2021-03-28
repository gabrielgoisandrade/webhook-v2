package com.gga.webhook.services

import com.gga.webhook.models.RepositoryModel
import com.gga.webhook.models.dTO.RepositoryDto

interface RepositoryService {

    fun saveRepository(repository: RepositoryModel)

    fun findRepositoryByName(name: String): RepositoryDto

    fun findRepositoryByEventAction(action: String): RepositoryDto

}