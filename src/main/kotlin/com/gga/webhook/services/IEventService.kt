package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto

interface IEventService {

    fun savePayload(payload: PayloadDto): PayloadDto

    fun saveIssue(issue: IssueModel): IssueModel

    fun saveUser(user: UserModel): UserModel

    fun saveAssignee(assignee: AssigneesModel): AssigneesModel

    fun saveAssignees(assignees: AssigneesModel): AssigneesModel

    fun saveLabel(label: LabelModel): LabelModel

    fun saveMilestone(milestone: MilestoneModel): MilestoneModel

    fun saveCreator(creator: CreatorModel): CreatorModel

    fun saveRepository(repository: RepositoryModel): RepositoryModel

    fun saveLicense(license: LicenseModel): LicenseModel

    fun saveOwner(owner: OwnerModel): OwnerModel

    fun saveSender(sender: SenderModel): SenderModel

    fun getIssueByNumber(number: Int): IssueDto

}