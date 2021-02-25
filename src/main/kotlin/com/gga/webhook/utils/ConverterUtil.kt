package com.gga.webhook.utils

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.dto.AssigneesDto
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.LabelsDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo

class ConverterUtil private constructor() {
    companion object {

        fun PayloadDto.toModel(): PayloadModel {
            val labelsModel: Set<LabelsModel> = this.issue!!.labels convertTo LabelsModel::class.java

            val assigneesModel: Set<AssigneesModel> = this.issue!!.assignees convertTo AssigneesModel::class.java

            val issueModel: IssueModel = (this.issue!! convertTo IssueModel::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }

            return (this convertTo PayloadModel::class.java).apply { this.issue = issueModel }
        }

        fun PayloadModel.toDto(): PayloadDto {
            val labelsModel: Set<LabelsDto> = this.issue!!.labels convertTo LabelsDto::class.java

            val assigneesModel: Set<AssigneesDto> = this.issue!!.assignees convertTo AssigneesDto::class.java

            val issueModel: IssueDto = (this.issue convertTo IssueDto::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }

            return (this convertTo PayloadDto::class.java).apply { this.issue = issueModel }
        }

    }
}