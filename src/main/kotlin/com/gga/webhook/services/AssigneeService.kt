package com.gga.webhook.services

import com.gga.webhook.models.vO.AssigneeVo

interface AssigneeService {

    fun getAssignee(): AssigneeVo?

}