package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.AssigneeNotFoundException
import com.gga.webhook.models.vO.AssigneesVo
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.services.AssigneesService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AssigneesServiceImpl @Autowired constructor(
    private val assigneesRepository: AssigneesRepository
) : AssigneesService {

    override fun getAssignees(): HashSet<AssigneesVo> =
        (this.assigneesRepository.getAssignees() convertTo AssigneesVo::class.java).toHashSet()

    override fun getAssigneeById(id: Long): AssigneesVo = this.assigneesRepository.findById(id).orElseThrow {
        AssigneeNotFoundException("Assignee with ID $id not found.")
    } convertTo AssigneesVo::class.java

}
