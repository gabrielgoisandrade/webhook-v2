package com.gga.webhook.services

import com.gga.webhook.models.dto.*

interface IEventService {

    fun savePayload(payloadDto: PayloadDto): PayloadDto

    fun saveIssue(issueDto: IssueDto): IssueDto

    fun saveUser(userDto: UserDto): UserDto

    fun saveAssignee(assigneeDto: AssigneeDto): AssigneeDto

    fun saveAssignees(assigneesDto: AssigneesDto): AssigneesDto

    fun saveLabel(labelDto: LabelDto): LabelDto

    fun saveMilestone(milestoneDto: MilestoneDto): MilestoneDto

    fun saveCreator(creatorDto: CreatorDto): CreatorDto

    fun saveRepository(repositoryDto: RepositoryDto): RepositoryDto

    fun saveLicense(licenseDto: LicenseDto): LicenseDto

    fun saveOwner(ownerDto: OwnerDto): OwnerDto

    fun saveSender(senderDto: SenderDto): SenderDto

    fun getIssueByNumber(issueId: Int): IssueDto

}