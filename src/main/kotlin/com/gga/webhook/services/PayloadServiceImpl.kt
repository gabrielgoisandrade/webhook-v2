package com.gga.webhook.services

import com.gga.webhook.errors.exceptions.InvalidDirectionException
import com.gga.webhook.errors.exceptions.PayloadNotFoundException
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
import com.gga.webhook.models.vO.PayloadVo
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PayloadServiceImpl @Autowired constructor(
    private val payloadRepository: PayloadRepository,
    private val issueRepository: IssueRepository,
    private val userRepository: UserRepository,
    private val assigneeRepository: AssigneeRepository,
    private val assigneesRepository: AssigneesRepository,
    private val labelsRepository: LabelsRepository,
    private val milestoneRepository: MilestoneRepository,
    private val creatorRepository: CreatorRepository,
    private val repositoryRepository: RepositoryRepository,
    private val licenseRepository: LicenseRepository,
    private val ownerRepository: OwnerRepository,
    private val senderRepository: SenderRepository
) : PayloadService {

    private lateinit var assigneesDto: Set<AssigneesDto>

    private lateinit var labelsDto: Set<LabelsDto>

    @Transactional
    override fun savePayload(payload: PayloadDto): PayloadDto =
        (payload convertTo PayloadModel::class.java).apply {
            this.issue = saveIssue(payload.issue!!)
            this.repository = saveRepository(payload.repository!!)
            this.sender = saveSender(payload.sender!!)
        }.run {
            (payloadRepository.save(this) convertTo PayloadDto::class.java).apply {
                this.issue!!.labels = labelsDto
                this.issue!!.assignees = assigneesDto
            }
        }

    @Transactional
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

        return repositoryModel.apply {
            this.owner = saveOwner(repository.owner!!)
            this.license = repository.license?.let { saveLicense(it) }
        }.run { repositoryRepository.save(this) }
    }

    @Transactional
    override fun saveLicense(license: LicenseDto): LicenseModel =
        (license convertTo LicenseModel::class.java).run { licenseRepository.save(this) }

    @Transactional
    override fun saveOwner(owner: OwnerDto): OwnerModel =
        (owner convertTo OwnerModel::class.java).run { ownerRepository.save(this) }

    @Transactional
    override fun saveSender(sender: SenderDto): SenderModel =
        (sender convertTo SenderModel::class.java).run { senderRepository.save(this) }

    override fun getPayloadById(id: Long): PayloadVo = this.payloadRepository.findById(id).orElseThrow {
        PayloadNotFoundException("Payload with ID $id not found.")
    }.run { this convertTo PayloadVo::class.java }

    override fun getAllPayloads(page: Int, limit: Int, direction: String): Page<PayloadVo> {
        val sort: Sort = this.verifyDirection(direction)

        return PageRequest.of(page, limit, sort).run {
            payloadRepository.findAll(this).map { it convertTo PayloadVo::class.java }
        }

    }

    private fun verifyDirection(direction: String): Sort = when (direction) {
        "asc" -> Sort.by("id").ascending()

        "desc" -> Sort.by("id").descending()

        else -> throw InvalidDirectionException("Direction $direction isn't a valid direction.")
    }

}