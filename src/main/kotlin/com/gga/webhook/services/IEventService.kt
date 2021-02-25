package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto

interface IEventService {

    fun savePayload(payload: PayloadDto): PayloadDto

    fun saveIssue(issue: IssueModel, fk: PayloadModel): IssueModel

    fun saveUser(user: UserModel, fk: IssueModel): UserModel

    fun saveAssignee(assignee: AssigneeModel, fk: IssueModel): AssigneeModel

    fun saveAssignees(assignees: Set<AssigneesModel>, fk: IssueModel): HashSet<AssigneesModel>

    fun saveLabels(labels: Set<LabelsModel>, fk: IssueModel): HashSet<LabelsModel>

    fun saveMilestone(milestone: MilestoneModel, fk: IssueModel): MilestoneModel

    fun saveCreator(creator: CreatorModel, fk: MilestoneModel): CreatorModel

    fun saveRepository(repository: RepositoryModel, fk: PayloadModel): RepositoryModel

    fun saveLicense(license: LicenseModel, fk: RepositoryModel): LicenseModel

    fun saveOwner(owner: OwnerModel, fk: RepositoryModel): OwnerModel

    fun saveSender(sender: SenderModel, fk: PayloadModel): SenderModel

    fun getIssueByNumber(number: Int): IssueDto

}