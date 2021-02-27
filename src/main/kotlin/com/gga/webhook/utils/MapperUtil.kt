@file:Suppress("unused")

package com.gga.webhook.utils

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.dto.AssigneesDto
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.LabelsDto
import com.gga.webhook.models.dto.PayloadDto
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies.STRICT
import java.util.stream.Collectors

class MapperUtil private constructor() {
    companion object {

        private val mapper: ModelMapper = ModelMapper().apply {
            with(this.configuration) {
                this.isAmbiguityIgnored = true
                this.isFieldMatchingEnabled = true
                this.matchingStrategy = STRICT
            }
        }

        @JvmStatic
        infix fun <O, D> Collection<O>.convertTo(to: Class<D>): Set<D> =
            this.stream().map { mapper.map(it, to) }.collect(Collectors.toSet())

        @JvmStatic
        infix fun <O, D> O.convertTo(to: Class<D>): D = mapper.map(this, to)

        @JvmStatic
        fun PayloadDto.toModel(): PayloadModel {
            val labelsModel: Set<LabelsModel> = this.issue!!.labels convertTo LabelsModel::class.java

            val assigneesModel: Set<AssigneesModel> = this.issue!!.assignees convertTo AssigneesModel::class.java

            val issueModel: IssueModel = (this.issue!! convertTo IssueModel::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }

            return (this convertTo PayloadModel::class.java).apply { this.issue = issueModel }
        }

        @JvmStatic
        fun PayloadModel.toDto(): PayloadDto {
            val labelsModel: Set<LabelsDto> = this.issue!!.labels convertTo LabelsDto::class.java

            val assigneesModel: Set<AssigneesDto> = this.issue!!.assignees convertTo AssigneesDto::class.java

            val issueModel: IssueDto = (this.issue convertTo IssueDto::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }

            return (this convertTo PayloadDto::class.java).apply { this.issue = issueModel }
        }

        @JvmStatic
        fun IssueDto.toModel(): IssueModel {
            val labelsModel: Set<LabelsModel> = this.labels convertTo LabelsModel::class.java

            val assigneesModel: Set<AssigneesModel> = this.assignees convertTo AssigneesModel::class.java

            return (this convertTo IssueModel::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }
        }

        @JvmStatic
        fun IssueModel.toDto(): IssueDto {
            val labelsModel: Set<LabelsDto> = this.labels convertTo LabelsDto::class.java

            val assigneesModel: Set<AssigneesDto> = this.assignees convertTo AssigneesDto::class.java

            return (this convertTo IssueDto::class.java).apply {
                this.labels = labelsModel
                this.assignees = assigneesModel
            }
        }

    }
}