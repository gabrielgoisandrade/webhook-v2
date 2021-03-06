package com.gga.webhook.services

import com.gga.webhook.models.vO.AssigneesVo

interface AssigneesService {

    fun getAssignees(): HashSet<AssigneesVo>

    fun getAssigneeById(id: Long): AssigneesVo?

}