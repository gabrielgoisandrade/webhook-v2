package com.gga.webhook.services

import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto

interface PayloadService {

    fun savePayload(payload: PayloadDto): EventDto

    //fun saveIssue(issue: IssueDto): IssueModel

    //fun saveUser(user: UserDto): UserModel

    // fun saveAssignee(assignee: AssigneeDto): AssigneeModel

    // fun saveAssignees(assignees: Set<AssigneesDto>, issue: IssueModel): HashSet<AssigneesModel>

    //  fun saveLabels(labels: Set<LabelsDto>, issue: IssueModel): HashSet<LabelsModel>

    //  fun saveMilestone(milestone: MilestoneDto): MilestoneModel

//    fun saveCreator(creator: CreatorDto): CreatorModel

    //  fun saveRepository(repository: RepositoryDto): RepositoryModel

    //   fun saveLicense(license: LicenseDto): LicenseModel

    //   fun saveOwner(owner: OwnerDto): OwnerModel

    //   fun saveSender(sender: SenderDto): SenderModel

    //   fun getPayloadById(id: Long): PayloadVo

    //   fun getAllPayloads(page: Int = 0, limit: Int = 10, sort: String): Page<PayloadVo>

}