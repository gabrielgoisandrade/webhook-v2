package com.gga.webhook.helper

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo

class ConverterHelper {

    private lateinit var payloadModel: PayloadModel

    private lateinit var issueModel: IssueModel

    private lateinit var labelsModel: Set<LabelModel>

    private lateinit var assigneesModel: Set<AssigneesModel>

    fun converter(payloadDto: PayloadDto): PayloadModel = with(payloadDto) {
        payloadModel = this convertTo PayloadModel::class.java

        payloadModel.apply { this.issue = convertIssue(this@with.issue) }
    }

    fun convertIssue(issueDto: IssueDto): IssueModel = with(issueDto) {
        issueModel = this convertTo IssueModel::class.java

        labelsModel = if (this.labels.isEmpty()) setOf() else this.labels convertTo LabelModel::class.java

        assigneesModel = if (this.assignees.isEmpty()) setOf() else this.assignees convertTo AssigneesModel::class.java

        issueModel.apply {
            this.labels = labelsModel
            this.assignees = assigneesModel
        }
    }

}