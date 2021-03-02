package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.*

interface IEventService {

    fun savePayload(payload: PayloadDto): PayloadDto

    fun saveIssue(issue: IssueDto): IssueModel

    fun saveUser(user: UserDto): UserModel

    fun saveAssignee(assignee: AssigneeDto): AssigneeModel

    fun saveAssignees(assignees: Set<AssigneesDto>, issue: IssueModel): HashSet<AssigneesModel>

    fun saveLabels(labels: Set<LabelsDto>, issue: IssueModel): HashSet<LabelsModel>

    fun saveMilestone(milestone: MilestoneDto): MilestoneModel

    fun saveCreator(creator: CreatorDto): CreatorModel

    fun saveRepository(repository: RepositoryDto): RepositoryModel

    fun saveLicense(license: LicenseDto): LicenseModel

    fun saveOwner(owner: OwnerDto): OwnerModel

    fun saveSender(sender: SenderDto): SenderModel

    fun getIssueByNumber(number: Int): HashSet<IssueDto>

}