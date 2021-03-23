package com.gga.webhook.services

import com.gga.webhook.models.OwnerModel
import com.gga.webhook.models.dTO.OwnerDto

interface OwnerService {

    fun saveOwner(owner: OwnerModel): OwnerModel

    fun findOwnerByRepositoryName(repositoryName: String): OwnerDto

}
