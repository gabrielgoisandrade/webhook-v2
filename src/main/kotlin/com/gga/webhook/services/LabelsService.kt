package com.gga.webhook.services

import com.gga.webhook.models.vO.LabelsVo

interface LabelsService {

    fun getLabels(): HashSet<LabelsVo>

    fun getLabelById(id: Long): LabelsVo?

}