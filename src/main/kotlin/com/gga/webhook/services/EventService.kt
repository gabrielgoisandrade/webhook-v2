package com.gga.webhook.services

import com.gga.webhook.models.dto.*
import com.gga.webhook.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventService @Autowired constructor(
    private val payloadRepository: PayloadRepository,
    private val issueRepository: IssueRepository,
    private val userRepository: UserRepository,
    private val assigneeRepository: AssigneeRepository,
    private val assigneesRepository: AssigneesRepository,
    private val labelRepository: LabelRepository,
    private val milestoneRepository: MilestoneRepository,
    private val creatorRepository: CreatorRepository,
    private val repositoryRepository: RepositoryRepository,
    private val licenseRepository: LicenseRepository,
    private val ownerRepository: OwnerRepository,
    private val senderRepository: SenderRepository,
) : IEventService {

    override fun savePayload(payloadDto: PayloadDto): PayloadDto {
        TODO("Not yet implemented")
    }

    override fun saveIssue(issueDto: IssueDto): IssueDto {
        TODO("Not yet implemented")
    }

    override fun saveUser(userDto: UserDto): UserDto {
        TODO("Not yet implemented")
    }

    override fun saveAssignee(assigneeDto: AssigneeDto): AssigneeDto {
        TODO("Not yet implemented")
    }

    override fun saveAssignees(assigneesDto: AssigneesDto): AssigneesDto {
        TODO("Not yet implemented")
    }

    override fun saveLabel(labelDto: LabelDto): LabelDto {
        TODO("Not yet implemented")
    }

    override fun saveMilestone(milestoneDto: MilestoneDto): MilestoneDto {
        TODO("Not yet implemented")
    }

    override fun saveCreator(creatorDto: CreatorDto): CreatorDto {
        TODO("Not yet implemented")
    }

    override fun saveRepository(repositoryDto: RepositoryDto): RepositoryDto {
        TODO("Not yet implemented")
    }

    override fun saveLicense(licenseDto: LicenseDto): LicenseDto {
        TODO("Not yet implemented")
    }

    override fun saveOwner(ownerDto: OwnerDto): OwnerDto {
        TODO("Not yet implemented")
    }

    override fun saveSender(senderDto: SenderDto): SenderDto {
        TODO("Not yet implemented")
    }

}