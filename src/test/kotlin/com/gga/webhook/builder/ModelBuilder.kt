package com.gga.webhook.builder

import com.gga.webhook.models.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo

class ModelBuilder {

    private val builder: DtoBuilder = DtoBuilder()

    val issue: IssueModel = (this.builder.issueDto() convertTo IssueModel::class.java)

    val assignee: AssigneeModel = this.builder.assigneeDto() convertTo AssigneeModel::class.java

    val assignees: AssigneesModel = this.builder.assigneesDto() convertTo AssigneesModel::class.java

    val labels: LabelsModel = this.builder.labelsDto() convertTo LabelsModel::class.java

    val user: UserModel = this.builder.userDto() convertTo UserModel::class.java

    val creator: CreatorModel = this.builder.creatorDto() convertTo CreatorModel::class.java

    val milestone: MilestoneModel = this.builder.milestoneDto() convertTo MilestoneModel::class.java

    val repository: RepositoryModel = this.builder.repositoryDto() convertTo RepositoryModel::class.java

    val owner: OwnerModel = this.builder.ownerDto() convertTo OwnerModel::class.java

    val license: LicenseModel = this.builder.licenseDto() convertTo LicenseModel::class.java

    val sender: SenderModel = this.builder.senderDto() convertTo SenderModel::class.java

    val event: EventModel = this.builder.payloadDto() convertTo EventModel::class.java

}