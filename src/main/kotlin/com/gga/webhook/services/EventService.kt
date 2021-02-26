package com.gga.webhook.services

import com.gga.webhook.models.*
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.MapperUtil.Companion.toDto
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

    override fun savePayload(payload: PayloadDto): PayloadDto {
        val payloadModel = PayloadModel(action = payload.action)

        this.payloadRepository.save(payloadModel).apply {
            this.issue = saveIssue(this.issue!!, this)
            this.repository = saveRepository(this.repository!!, this)
            this.sender = saveSender(this.sender!!, this)
        }.run {
            return payloadRepository.save(this).toDto()
        }
    }

    override fun saveIssue(issue: IssueModel, fk: PayloadModel): IssueModel {
        val labels: Set<LabelsModel> = issue.labels

        val assignee: AssigneeModel? = issue.assignee

        val assignees: Set<AssigneesModel> = issue.assignees

        val user: UserModel = issue.user!!

        val milestone: MilestoneModel? = issue.milestone

        val issueModel: IssueModel = issue.apply {
            this.labels = hashSetOf()
            this.assignee = null
            this.assignees = hashSetOf()
            this.user = null
            this.milestone = null
            this.payload = fk
        }

        issueRepository.save(issueModel).apply {
            this.labels = saveLabels(labels, this)
            this.assignee = assignee?.let { saveAssignee(it, this) }
            this.assignees = saveAssignees(assignees, this)
            this.user = saveUser(user, this)
            this.milestone = milestone?.let { saveMilestone(it, this) }
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

    override fun saveMilestone(milestone: MilestoneModel, fk: IssueModel): MilestoneModel {
        val creator: CreatorModel = milestone.creator!!
        val milestoneModel: MilestoneModel = milestone.apply {
            this.creator = null
            this.issue = fk
        }

        this.milestoneRepository.save(milestoneModel).apply {
            this.creator = saveCreator(creator, this)
        }.run {
            return milestoneRepository.save(this)
        }
    }

    override fun saveCreator(creator: CreatorModel, fk: MilestoneModel): CreatorModel =
            creator.apply { this.milestone = fk }.run { creatorRepository.save(this) }

    override fun saveRepository(repository: RepositoryModel, fk: PayloadModel): RepositoryModel {
        val owner: OwnerModel = repository.owner!!

        val license: LicenseModel? = repository.license

        val repositoryModel: RepositoryModel = repository.apply {
            this.owner = null
            this.license = null
            this.payload = fk
        }

        this.repositoryRepository.save(repositoryModel).apply {
            this.owner = saveOwner(owner, this)
            this.license = license?.let { saveLicense(it, this) }
        }.run {
            return repositoryRepository.save(this)
        }
    }

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