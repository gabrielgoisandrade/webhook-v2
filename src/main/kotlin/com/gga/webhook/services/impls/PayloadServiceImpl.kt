package com.gga.webhook.services.impls

import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
import com.gga.webhook.services.PayloadService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service

@Service
@EnableCaching
class PayloadServiceImpl @Autowired constructor(
    private val eventServiceImpl: EventServiceImpl,
    private val repositoryServiceImpl: RepositoryServiceImpl,
    private val senderServiceImpl: SenderServiceImpl,
    private val ownerServiceImpl: OwnerServiceImpl,
    private val creatorServiceImpl: CreatorServiceImpl,
    private val milestoneServiceImpl: MilestoneServiceImpl,
    private val userServiceImpl: UserServiceImpl,
    private val assigneeServiceImpl: AssigneeServiceImpl,
    private val issueServiceImpl: IssueServiceImpl,
    private val licenseServiceImpl: LicenseServiceImpl,
    private val labelsServiceImpl: LabelsServiceImpl,
    private val assigneesServiceImpl: AssigneesServiceImpl,
    private val issueResponsibleServiceImpl: IssueResponsibleServiceImpl,
    private val issueClassifierServiceImpl: IssueClassifierServiceImpl
) : PayloadService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun savePayloadData(payload: PayloadDto): EventDto {
        val savedEvent: EventModel = this.saveEvent(payload.action)

        val savedLabels: List<LabelsModel> = this.saveLabels(payload.issue!!.labels)

        val savedAssignees: List<AssigneesModel> = this.saveAssignees(payload.issue!!.assignees)

        with(savedEvent) {
            saveIssue(payload.issue!!, this).also {
                saveClassifiers(it, savedLabels)
                saveResponsible(it, savedAssignees)
            }

            saveRepository(payload.repository!!, this)

            saveSender(payload.sender!!, this)
        }

        return savedEvent convertTo EventDto::class.java
    }

    override fun saveEvent(action: String): EventModel =
        EventModel(action = action).run { eventServiceImpl.saveEvent(this) }

    override fun saveIssue(issue: IssueDto, fk: EventModel): IssueModel =
        (issue convertTo IssueModel::class.java).run {
            this.user = saveUser(issue.user!!)
            this.assignee = saveAssignee(issue.assignee)
            this.milestone = saveMilestone(issue.milestone)
            this.event = fk
            issueServiceImpl.saveIssue(this)
        }

    override fun saveRepository(repository: RepositoryDto, fk: EventModel): Unit =
        (repository convertTo RepositoryModel::class.java).run {
            this.owner = saveOwner(repository.owner!!)
            this.license = saveLicense(repository.license)
            this.event = fk
            repositoryServiceImpl.saveRepository(this)
        }

    override fun saveSender(sender: SenderDto, fk: EventModel): Unit =
        (sender convertTo SenderModel::class.java).run {
            this.event = fk
            senderServiceImpl.saveSender(this)
        }

    override fun saveUser(user: UserDto): UserModel =
        (user convertTo UserModel::class.java).run { userServiceImpl.saveUser(this) }

    override fun saveAssignee(assignee: AssigneeDto?): AssigneeModel? =
        if (assignee == null) {
            this.log.info("Payload: No Assignees to save.")
            null
        } else {
            (assignee convertTo AssigneeModel::class.java).run { assigneeServiceImpl.saveAssignee(this) }
        }

    override fun saveAssignees(assignees: List<AssigneesDto>): List<AssigneesModel> =
        if (assignees.isEmpty()) {
            this.log.info("Payload: No Assignees to save.")
            emptyList()
        } else {
            (assignees convertTo AssigneesModel::class.java).run { assigneesServiceImpl.saveAssignees(this) }
        }

    override fun saveLabels(labels: List<LabelsDto>): List<LabelsModel> =
        if (labels.isEmpty()) {
            this.log.info("Payload: No Labels to save.")
            emptyList()
        } else {
            (labels convertTo LabelsModel::class.java).run { labelsServiceImpl.saveLabels(this) }
        }

    override fun saveMilestone(milestone: MilestoneDto?): MilestoneModel? =
        if (milestone == null) {
            this.log.info("Payload: No Milestone to save.")
            null
        } else {
            (milestone convertTo MilestoneModel::class.java).run {
                this.creator = saveCreator(milestone.creator!!)
                milestoneServiceImpl.saveMilestone(this)
            }
        }

    override fun saveCreator(creator: CreatorDto): CreatorModel =
        (creator convertTo CreatorModel::class.java).run { creatorServiceImpl.saveCreator(this) }

    override fun saveOwner(owner: OwnerDto): OwnerModel =
        (owner convertTo OwnerModel::class.java).run { ownerServiceImpl.saveOwner(this) }

    override fun saveLicense(license: LicenseDto?): LicenseModel? =
        if (license == null) {
            this.log.info("Payload: No License to save.")
            null
        } else {
            (license convertTo LicenseModel::class.java).run { licenseServiceImpl.saveLicense(this) }
        }

    override fun saveClassifiers(issue: IssueModel, labels: List<LabelsModel>): Unit =
        if (labels.isEmpty())
            this.log.info("Payload: No Labels found. No classifiers to save.")
        else
            this.issueClassifierServiceImpl.saveIssueClassifier(issue, labels)

    override fun saveResponsible(issue: IssueModel, assignees: List<AssigneesModel>): Unit =
        if (assignees.isEmpty())
            this.log.info("Payload: No Assignees found. No responsible to save.")
        else
            this.issueResponsibleServiceImpl.saveIssueResponsible(issue, assignees)

}