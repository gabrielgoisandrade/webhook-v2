package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.ConverterUtil.Companion.toDto
import com.gga.webhook.utils.ConverterUtil.Companion.toModel
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
    private val senderRepository: SenderRepository
) : IEventService {

    override fun savePayload(payload: PayloadDto): PayloadDto =
        payload.toModel().apply {
            this.issue = saveIssue(this.issue!!, this)
            this.repository = saveRepository(this.repository!!, this)
            this.sender = saveSender(this.sender!!, this)
        }.run { payloadRepository.save(this).toDto() }

    override fun saveIssue(issue: IssueModel, fk: PayloadModel): IssueModel {
        issue.apply {
            this.labels = saveLabels(this.labels, this)
            this.assignee = this.assignee?.let { saveAssignee(it, this) }
            this.assignees = saveAssignees(this.assignees, this)
            this.user = saveUser(this.user!!, this)
            this.milestone = saveMilestone(this.milestone!!, this)
            this.payload = fk
        }.run {
            return issueRepository.save(this)
        }
    }

    override fun saveUser(user: UserModel, fk: IssueModel): UserModel =
        user.apply { this.issue = fk }.run { userRepository.save(this) }

    override fun saveAssignee(assignee: AssigneeModel, fk: IssueModel): AssigneeModel =
        assignee.apply { this.issue = fk }.run { assigneeRepository.save(this) }

    override fun saveAssignees(assignees: Set<AssigneesModel>, fk: IssueModel): HashSet<AssigneesModel> =
        assignees.run {
            this.map { it.issue = fk }
            assigneesRepository.saveAll(this)
        }.toHashSet()

    override fun saveLabels(labels: Set<LabelsModel>, fk: IssueModel): HashSet<LabelsModel> = labels.run {
        this.map { it.issue = fk }
        labelRepository.saveAll(this)
    }.toHashSet()

    override fun saveMilestone(milestone: MilestoneModel, fk: IssueModel): MilestoneModel = milestone.apply {
        this.creator = saveCreator(this.creator!!, this)
        this.issue = fk
    }.run { milestoneRepository.save(this) }

    override fun saveCreator(creator: CreatorModel, fk: MilestoneModel): CreatorModel =
        creator.apply { this.milestone = fk }.run { creatorRepository.save(this) }

    override fun saveRepository(repository: RepositoryModel, fk: PayloadModel): RepositoryModel = repository.apply {
        this.owner = saveOwner(this.owner!!, this)
        this.license = this.license?.let { saveLicense(it, this) }
        this.payload = fk
    }.run { repositoryRepository.save(this) }

    override fun saveLicense(license: LicenseModel, fk: RepositoryModel): LicenseModel =
        license.apply { this.repository = fk }.run { licenseRepository.save(this) }

    override fun saveOwner(owner: OwnerModel, fk: RepositoryModel): OwnerModel =
        owner.apply { this.repository = fk }.run { ownerRepository.save(this) }

    override fun saveSender(sender: SenderModel, fk: PayloadModel): SenderModel =
        sender.apply { this.payload = fk }.run { senderRepository.save(this) }

    override fun getIssueByNumber(number: Int): IssueDto {
        TODO("Not yet implemented")
    }

}