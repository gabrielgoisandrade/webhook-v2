package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.LabelNotFoundException
import com.gga.webhook.models.vO.LabelsVo
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.LabelsService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LabelsServiceImpl @Autowired constructor(
    private val labelsRepository: LabelsRepository
) : LabelsService {

    override fun getLabels(): HashSet<LabelsVo> =
        (this.labelsRepository.getLabels() convertTo LabelsVo::class.java).toHashSet()

    override fun getLabelById(id: Long): LabelsVo = this.labelsRepository.findById(id).orElseThrow {
        LabelNotFoundException("Label with ID $id not found.")
    } convertTo LabelsVo::class.java

}
