package com.gga.webhook.services

import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.LabelsDto

interface LabelsService {

    fun saveLabels(labels: HashSet<LabelsModel>): HashSet<LabelsModel>

    fun findLabelsByIssueNumber(issueNumber: Int): HashSet<LabelsDto>

}