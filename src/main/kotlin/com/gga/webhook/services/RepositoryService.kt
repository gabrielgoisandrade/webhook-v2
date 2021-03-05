package com.gga.webhook.services

import com.gga.webhook.models.vO.RepositoryVo

interface RepositoryService {

    fun getRepository(): RepositoryVo?

}