package com.gga.webhook.services.impls

import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.services.PayloadService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
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

    override fun savePayload(payload: PayloadDto): EventDto {
        val event = EventDto(action = payload.action)

        val issue: IssueModel = payload.issue convertTo IssueModel::class.java

        val user: UserModel = payload.issue!!.user convertTo UserModel::class.java

        val labels: List<LabelsModel> = payload.issue!!.labels convertTo LabelsModel::class.java

        val assignees: List<AssigneesModel> = payload.issue!!.assignees convertTo AssigneesModel::class.java

        val milestone: MilestoneModel? = this.verifyNullable(payload.issue?.milestone)

        val creator: CreatorModel? = this.verifyNullable(payload.issue?.milestone?.creator)

        val license: LicenseModel? = this.verifyNullable(payload.repository?.license)

        val assignee: AssigneeModel? = this.verifyNullable(payload.issue?.assignee)

        val owner: OwnerModel = payload.repository!!.owner convertTo OwnerModel::class.java

        val repository: RepositoryModel = payload.repository convertTo RepositoryModel::class.java

        val sender: SenderModel = payload.sender convertTo SenderModel::class.java

        this.licenseServiceImpl.saveLicense(license).also { repository.license = it }

        this.ownerServiceImpl.saveOwner(owner).also { repository.owner = it }

        this.userServiceImpl.saveUser(user).also { issue.user = it }

        this.assigneeServiceImpl.saveAssignee(assignee).also { issue.assignee = it }

        this.creatorServiceImpl.saveCreator(creator).also { milestone?.creator = it }

        this.milestoneServiceImpl.saveMilestone(milestone).also { issue.milestone = it }

        var labelsSaved: List<LabelsModel>

        var assigneesSaved: List<AssigneesModel>

        this.labelsServiceImpl.saveLabels(labels).also { labelsSaved = it }

        this.assigneesServiceImpl.saveAssignees(assignees).also { assigneesSaved = it }

        return this.eventServiceImpl.saveEvent(event).also {
            this.issueServiceImpl.saveIssue(issue.apply { this.event = it }).also { toSave ->
                this.issueClassifierServiceImpl.saveIssueClassifier(toSave, labelsSaved)
                this.issueResponsibleServiceImpl.saveIssueResponsible(toSave, assigneesSaved)
            }

            this.repositoryServiceImpl.saveRepository(repository.apply { this.event = it })

            this.senderServiceImpl.saveSender(sender.apply { this.event = it })
        } convertTo EventDto::class.java

    }

    private inline fun <O, reified D> verifyNullable(value: O?): D? =
        if (value == null) null else value convertTo D::class.java

    /*@Transactional
    override fun saveIssue(issue: IssueDto): IssueModel =
        (issue convertTo IssueModel::class.java).apply {
            this.assignee = issue.assignee?.let { saveAssignee(it) }
            this.user = saveUser(issue.user!!)
            this.milestone = issue.milestone?.let { saveMilestone(it) }
        }.run {
            issueRepository.save(this)
        }.also {
            this.assigneesDto = this.saveAssignees(issue.assignees, it) convertTo AssigneesDto::class.java
            this.labelsDto = this.saveLabels(issue.labels, it) convertTo LabelsDto::class.java
        }

    @Transactional
    override fun saveUser(user: UserDto): UserModel =
        (user convertTo UserModel::class.java).run { userRepository.save(this) }

    @Transactional
    override fun saveAssignee(assignee: AssigneeDto): AssigneeModel =
        (assignee convertTo AssigneeModel::class.java).run { assigneeRepository.save(this) }

    @Transactional
    override fun saveAssignees(assignees: Set<AssigneesDto>, issue: IssueModel): HashSet<AssigneesModel> {
        if (assignees.isEmpty()) hashSetOf<AssigneeModel>()

        return (assignees convertTo AssigneesModel::class.java).run {
            this.map { it.issue = issue }
            assigneesRepository.saveAll(this)
        }.toHashSet()
    }

    @Transactional
    override fun saveLabels(labels: Set<LabelsDto>, issue: IssueModel): HashSet<LabelsModel> {
        if (labels.isEmpty()) hashSetOf<AssigneeModel>()

        return (labels convertTo LabelsModel::class.java).run {
            this.map { it.issue = issue }
            labelsRepository.saveAll(this)
        }.toHashSet()
    }

    @Transactional
    override fun saveMilestone(milestone: MilestoneDto): MilestoneModel =
        (milestone convertTo MilestoneModel::class.java).apply {
            this.creator = saveCreator(milestone.creator!!)
        }.run { milestoneRepository.save(this) }

    @Transactional
    override fun saveCreator(creator: CreatorDto): CreatorModel =
        (creator convertTo CreatorModel::class.java).run { creatorRepository.save(this) }

    @Transactional
    override fun saveRepository(repository: RepositoryDto): RepositoryModel {
        val repositoryModel: RepositoryModel = repository convertTo RepositoryModel::class.java
        repository.license?.let { saveLicense(it) }
        return repositoryModel.apply {
            this.owner = saveOwner(repository.owner!!)
            //this.license =
        }.run { repositoryRepository.save(this) }
    }

    @Transactional
    override fun saveLicense(license: LicenseDto): LicenseModel {
        val repo = this.payload.repository convertTo RepositoryModel::class.java
        val licenseModel = license convertTo LicenseModel::class.java
        repo.license = null
        licenseModel.repositories = setOf(repo)

        val run = licenseModel.run { licenseRepository.save(this) }

        val update = this.repositoryRepository.findAll().last()
        run.apply { this.repositories = null }

        this.repositoryRepository.updateFk(run, 14)

        println(run)

        return run
    }

    @Transactional
    override fun saveOwner(owner: OwnerDto): OwnerModel =
        (owner convertTo OwnerModel::class.java).run { ownerRepository.save(this) }

    @Transactional
    override fun saveSender(sender: SenderDto): SenderModel =
        (sender convertTo SenderModel::class.java).run { senderRepository.save(this) }

    @Cacheable(cacheNames = ["payloadById"])
    override fun getPayloadById(id: Long): PayloadVo = this.payloadRepository.findById(id).orElseThrow {
        PayloadNotFoundException("Payload with ID $id not found.")
    }.run { this convertTo PayloadVo::class.java }

    override fun getAllPayloads(page: Int, limit: Int, sort: String): Page<PayloadVo> {
        val sort: Sort = this.verifySort(sort)

        return PageRequest.of(page, limit, sort).run {
            payloadRepository.findAll(this).map { it convertTo PayloadVo::class.java }
        }
    }

    private fun verifySort(sort: String): Sort = when (sort) {
        "asc" -> Sort.by("id").ascending()

        "desc" -> Sort.by("id").descending()

        else -> throw InvalidSortException("Sort $sort isn't a valid sort.")
    }*/

}