package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*

interface PayloadService {

    fun savePayloadData(payload: PayloadDto): EventDto

    fun saveEvent(action: String): EventModel

    fun saveIssue(issue: IssueDto, fk: EventModel): IssueModel

    fun saveRepository(repository: RepositoryDto, fk: EventModel)

    fun saveSender(sender: SenderDto, fk: EventModel)

    fun saveUser(user: UserDto): UserModel

    fun saveAssignee(assignee: AssigneeDto?): AssigneeModel?

    fun saveAssignees(assignees: List<AssigneesDto>): List<AssigneesModel>

    fun saveLabels(labels: List<LabelsDto>): List<LabelsModel>

    fun saveMilestone(milestone: MilestoneDto?): MilestoneModel?

    fun saveCreator(creator: CreatorDto): CreatorModel

    fun saveLicense(license: LicenseDto?): LicenseModel?

    fun saveOwner(owner: OwnerDto): OwnerModel

    fun saveClassifiers(issue: IssueModel, labels: List<LabelsModel>)

    fun saveResponsible(issue: IssueModel, assignees: List<AssigneesModel>)

}