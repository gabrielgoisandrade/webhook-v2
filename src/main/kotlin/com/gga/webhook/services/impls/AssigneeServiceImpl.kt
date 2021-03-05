package com.gga.webhook.services.impls

import com.gga.webhook.models.vO.AssigneeVo
import com.gga.webhook.repositories.AssigneeRepository
import com.gga.webhook.services.AssigneeService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AssigneeServiceImpl @Autowired constructor(private val assigneeRepository: AssigneeRepository) : AssigneeService {

    override fun getAssignee(): AssigneeVo? = this.assigneeRepository.getAssignee() convertTo AssigneeVo::class.java

}