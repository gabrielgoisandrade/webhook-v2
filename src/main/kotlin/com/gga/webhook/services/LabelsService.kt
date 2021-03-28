package com.gga.webhook.services

import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.LabelsDto
import org.springframework.data.domain.Page

interface LabelsService {

    fun saveLabels(labels: List<LabelsModel>): List<LabelsModel>

    fun findLabelsByIssueNumber(issueNumber: Int, page: Int, limit: Int, sort: String): Page<LabelsDto>

}