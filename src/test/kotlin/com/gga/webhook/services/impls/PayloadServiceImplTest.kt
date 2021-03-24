package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired

internal class PayloadServiceImplTest : BaseServiceImplTestFactory() {

    @Autowired
    private lateinit var eventServiceImpl: EventServiceImpl

    @Autowired
    private lateinit var repositoryServiceImpl: RepositoryServiceImpl

    @Autowired
    private lateinit var senderServiceImpl: SenderServiceImpl

    @Autowired
    private lateinit var ownerServiceImpl: OwnerServiceImpl

    @Autowired
    private lateinit var licenseServiceImpl: LicenseServiceImpl

    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @Autowired
    private lateinit var assigneeServiceImpl: AssigneeServiceImpl

    @Autowired
    private lateinit var issueServiceImpl: IssueServiceImpl

    @Autowired
    private lateinit var creatorServiceImpl: CreatorServiceImpl

    @Autowired
    private lateinit var milestoneServiceImpl: MilestoneServiceImpl

    @Autowired
    private lateinit var labelsServiceImpl: LabelsServiceImpl

    @Autowired
    private lateinit var assigneesServiceImpl: AssigneesServiceImpl

    @Autowired
    private lateinit var issueResponsibleServiceImpl: IssueResponsibleServiceImpl

    @Autowired
    private lateinit var issueClassifierServiceImpl: IssueClassifierServiceImpl

    private val payloadServiceImpl: PayloadServiceImpl by lazy {
        PayloadServiceImpl(
            this.eventServiceImpl,
            this.repositoryServiceImpl,
            this.senderServiceImpl,
            this.ownerServiceImpl,
            this.creatorServiceImpl,
            this.milestoneServiceImpl,
            this.userServiceImpl,
            this.assigneeServiceImpl,
            this.issueServiceImpl,
            this.licenseServiceImpl,
            this.labelsServiceImpl,
            this.assigneesServiceImpl,
            this.issueResponsibleServiceImpl,
            this.issueClassifierServiceImpl
        )
    }

    private val payloadDto: PayloadDto = this.dto.payloadDto()

    private val eventModel: EventModel = this.model.event

    private val eventDto: EventDto = this.dto.eventDto()

    private val userModel: UserModel = this.model.user

    private val assigneeModel: AssigneeModel = this.model.assignee

    private val milestoneModel: MilestoneModel = this.model.milestone

    private val creatorModel: CreatorModel = this.model.creator

    private val repositoryModel: RepositoryModel = this.model.repository.apply { this.event = eventModel }

    private val ownerModel: OwnerModel = this.model.owner

    private val issueModel: IssueModel = this.model.issue.apply { this.event = eventModel }

    private val issueClassifierModel: IssueClassifierModel =
        IssueClassifierModel(issue = this.issueModel, labels = this.model.labels)

    private val issueResponsibleModel: IssueResponsibleModel =
        IssueResponsibleModel(issue = this.issueModel, assignees = this.model.assignees)

    private val assigneesModel: HashSet<AssigneesModel> = hashSetOf(this.model.assignees)

    private val senderModel: SenderModel = this.model.sender

    private val labelsModel: HashSet<LabelsModel> = hashSetOf(this.model.labels)

    private val assigneesDto: HashSet<AssigneesDto> = this.dto.assignees()

    private val labelsDto: HashSet<LabelsDto> = this.dto.labels()

    private val issueDto: IssueDto = this.dto.issueDto()

    private val assigneeDto: AssigneeDto? = this.dto.issueDto().assignee

    private val milestoneDto: MilestoneDto? = this.dto.issueDto().milestone

    @Test
    fun savePayloadData() {
        `when`(this.eventRepository.save(any(EventModel::class.java))).thenReturn(this.eventModel)

        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(this.labelsModel.toMutableList())

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(this.assigneesModel.toMutableList())

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(this.issueModel)

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.userModel)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.assigneeModel)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.milestoneModel)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.creatorModel)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.ownerModel)

        this.payloadServiceImpl.savePayloadData(this.payloadDto).also { assertThat(it).isEqualTo(this.eventDto) }

        verify(this.repositoryRepository).save(any(RepositoryModel::class.java))

        verify(this.licenseRepository).save(any(LicenseModel::class.java))

        verify(this.senderRepository).save(any(SenderModel::class.java))

        verify(this.issueClassifierRepository).save(any(IssueClassifierModel::class.java))

        verify(this.issueResponsibleRepository).save(any(IssueResponsibleModel::class.java))
    }

    @Test
    fun saveEvent() {
        `when`(this.eventRepository.save(any(EventModel::class.java))).thenReturn(this.eventModel)

        this.payloadServiceImpl.saveEvent(EVENT_ACTION).also { assertThat(it).isEqualTo(this.eventModel) }
    }

    @Test
    fun saveIssue() {
        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.userModel)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.assigneeModel)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.milestoneModel)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.creatorModel)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(this.issueModel)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.ownerModel)

        this.payloadServiceImpl.saveIssue(this.issueDto, this.eventModel).also {
            assertThat(it.user).isEqualTo(this.issueModel.user)
            assertThat(it.assignee).isNotNull.isEqualTo(this.issueModel.assignee)
            assertThat(it.milestone).isNotNull.isEqualTo(this.issueModel.milestone)
            assertThat(it.milestone!!.creator).isEqualTo(this.issueModel.milestone!!.creator)
            assertThat(it.event).isNotNull.isEqualTo(this.issueModel.event)
        }
    }

    @Test
    fun saveIssueWithoutAssignee() {
        val expectedIssue: IssueModel = this.issueModel.apply { this.assignee = null }

        val issueToSave: IssueDto = this.issueDto.apply { this.assignee = null }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.userModel)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.milestoneModel)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.creatorModel)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        this.payloadServiceImpl.saveIssue(issueToSave, this.eventModel).also {
            assertThat(it.user).isEqualTo(this.issueModel.user)
            assertThat(it.assignee).isNull()
            assertThat(it.milestone).isNotNull.isEqualTo(expectedIssue.milestone)
            assertThat(it.milestone!!.creator).isEqualTo(expectedIssue.milestone!!.creator)
            assertThat(it.event).isNotNull.isEqualTo(expectedIssue.event)
        }

        verify(this.assigneeRepository, never()).save(any(AssigneeModel::class.java))
    }

    @Test
    fun saveIssueWithoutMilestone() {
        val expectedIssue: IssueModel = this.issueModel.apply { this.milestone = null }

        val issueToSave: IssueDto = this.issueDto.apply { this.milestone = null }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.userModel)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.assigneeModel)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        this.payloadServiceImpl.saveIssue(issueToSave, this.eventModel).also {
            assertThat(it.user).isEqualTo(this.issueModel.user)
            assertThat(it.assignee).isNotNull.isEqualTo(expectedIssue.assignee)
            assertThat(it.milestone).isNull()
            assertThat(it.event).isNotNull.isEqualTo(expectedIssue.event)
        }

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))

        verify(this.creatorRepository, never()).save(any(CreatorModel::class.java))
    }

    @Test
    fun saveIssueWithoutAssigneeAndMilestone() {
        val expectedIssue: IssueModel = this.issueModel.apply {
            this.assignee = null
            this.milestone = null
        }

        val issueToSave: IssueDto = this.issueDto.apply {
            this.assignee = null
            this.milestone = null
        }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.userModel)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        this.payloadServiceImpl.saveIssue(issueToSave, this.eventModel).also {
            assertThat(it.user).isEqualTo(this.issueModel.user)
            assertThat(it.assignee).isNull()
            assertThat(it.milestone).isNull()
            assertThat(it.event).isNotNull.isEqualTo(expectedIssue.event)
        }

        verify(this.assigneeRepository, never()).save(any(AssigneeModel::class.java))

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))

        verify(this.creatorRepository, never()).save(any(CreatorModel::class.java))
    }

    @Test
    fun saveRepository() {
        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(this.repositoryModel)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.ownerModel)

        this.payloadServiceImpl.saveRepository(this.dto.repositoryDto(), this.eventModel)

        verify(this.repositoryRepository).save(any(RepositoryModel::class.java))

        verify(this.licenseRepository).save(any(LicenseModel::class.java))
    }

    @Test
    fun saveRepositoryWithoutLicense() {
        val expectedRepository: RepositoryModel = this.repositoryModel.apply {
            this.license = null
        }

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(expectedRepository)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.ownerModel)

        this.payloadServiceImpl.saveRepository(this.dto.repositoryWithoutLicense(), this.eventModel)

        verify(this.repositoryRepository).save(any(RepositoryModel::class.java))

        verify(this.licenseRepository, never()).save(any(LicenseModel::class.java))
    }

    @Test
    fun saveSender() {
        `when`(this.senderRepository.save(any(SenderModel::class.java))).thenReturn(this.senderModel)

        this.payloadServiceImpl.saveSender(this.dto.senderDto(), this.eventModel)

        verify(this.senderRepository).save(any(SenderModel::class.java))
    }

    @Test
    fun saveOwner() {
        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.ownerModel)

        this.payloadServiceImpl.saveOwner(this.dto.ownerDto()).also { assertThat(it).isEqualTo(this.ownerModel) }
    }

    @Test
    fun saveAssignee() {
        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.assigneeModel)

        this.payloadServiceImpl.saveAssignee(this.assigneeDto)

        verify(this.assigneeRepository).save(any(AssigneeModel::class.java))
    }

    @Test
    fun nullAssignee() {
        this.payloadServiceImpl.saveAssignee(null)

        verify(this.assigneeRepository, never()).save(any(AssigneeModel::class.java))
    }

    @Test
    fun saveMilestone() {
        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.milestoneModel)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.creatorModel)

        this.payloadServiceImpl.saveMilestone(this.milestoneDto)
    }

    @Test
    fun nullMilestone() {
        this.payloadServiceImpl.saveMilestone(null).also { assertThat(it).isNull() }

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))

        verify(this.creatorRepository, never()).save(any(CreatorModel::class.java))
    }

    @Test
    fun saveCreator() {
        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.creatorModel)

        this.payloadServiceImpl.saveCreator(this.dto.creatorDto())
    }

    @Test
    fun saveUser() {
        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.model.user)

        this.payloadServiceImpl.saveUser(this.dto.userDto())

        verify(this.userRepository).save(any(UserModel::class.java))
    }

    @Test
    fun saveAssignees() {
        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(this.assigneesModel.toMutableList())

        this.payloadServiceImpl.saveAssignees(this.assigneesDto).also {
            assertThat(it).isEqualTo(this.assigneesModel)
        }
    }

    @Test
    fun emptyAssignees() {
        this.payloadServiceImpl.saveAssignees(hashSetOf())

        verify(this.assigneesRepository, never()).saveAll((anyList()))
    }

    @Test
    fun saveLabels() {
        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(this.labelsModel.toMutableList())

        this.payloadServiceImpl.saveLabels(this.labelsDto).also {
            assertThat(it).isEqualTo(this.labelsModel)
        }
    }

    @Test
    fun emptyLabels() {
        this.payloadServiceImpl.saveLabels(hashSetOf())

        verify(this.labelsRepository, never()).saveAll((anyList()))
    }

    @Test
    fun saveClassifiers() {
        `when`(this.issueClassifierRepository.save(any(IssueClassifierModel::class.java)))
            .thenReturn(this.issueClassifierModel)

        this.payloadServiceImpl.saveClassifiers(this.issueModel, this.labelsModel)

        verify(this.issueClassifierRepository).save(any(IssueClassifierModel::class.java))
    }

    @Test
    fun emptyClassifiers() {
        this.payloadServiceImpl.saveClassifiers(this.issueModel, hashSetOf())

        verify(this.issueClassifierRepository, never()).save(any(IssueClassifierModel::class.java))
    }

    @Test
    fun saveResponsible() {
        `when`(this.issueResponsibleRepository.save(any(IssueResponsibleModel::class.java)))
            .thenReturn(this.issueResponsibleModel)

        this.payloadServiceImpl.saveResponsible(this.issueModel, this.assigneesModel)

        verify(this.issueResponsibleRepository).save(any(IssueResponsibleModel::class.java))
    }

    @Test
    fun emptyResponsible() {
        this.payloadServiceImpl.saveResponsible(this.issueModel, hashSetOf())

        verify(this.issueResponsibleRepository, never()).save(any(IssueResponsibleModel::class.java))
    }

}