package com.gga.webhook.services

import com.gga.webhook.errors.exceptions.IssueNotFound
import com.gga.webhook.helper.ConverterHelper
import com.gga.webhook.models.*
import com.gga.webhook.models.dto.AssigneesDto
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.LabelDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
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

    private val helper: ConverterHelper = ConverterHelper()

    override fun savePayload(payload: PayloadDto): PayloadDto {
        TODO("Not yet implemented")
    }

    override fun saveIssue(issue: IssueModel): IssueModel {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: UserModel): UserModel {
        TODO("Not yet implemented")
    }

    override fun saveAssignee(assignee: AssigneesModel): AssigneesModel {
        TODO("Not yet implemented")
    }

    override fun saveAssignees(assignees: AssigneesModel): AssigneesModel {
        TODO("Not yet implemented")
    }

    override fun saveLabel(label: LabelModel): LabelModel {
        TODO("Not yet implemented")
    }

    override fun saveMilestone(milestone: MilestoneModel): MilestoneModel {
        TODO("Not yet implemented")
    }

    override fun saveCreator(creator: CreatorModel): CreatorModel {
        TODO("Not yet implemented")
    }

    override fun saveRepository(repository: RepositoryModel): RepositoryModel {
        TODO("Not yet implemented")
    }

    override fun saveLicense(license: LicenseModel): LicenseModel {
        TODO("Not yet implemented")
    }

    override fun saveOwner(owner: OwnerModel): OwnerModel {
        TODO("Not yet implemented")
    }

    override fun saveSender(sender: SenderModel): SenderModel {
        TODO("Not yet implemented")
    }

    override fun getIssueByNumber(number: Int): IssueDto {
        val issueFound: IssueModel = this.issueRepository.findIssueModelByNumber(number) ?: throw
            IssueNotFound("Issue #$number not found")

        return (issueFound convertTo IssueDto::class.java).apply {
            this.labels = this.labels convertTo LabelDto::class.java
            this.assignees = this.assignees convertTo AssigneesDto::class.java
        }
    }
}