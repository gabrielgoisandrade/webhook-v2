package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.*
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.ConverterUtil.Companion.toDto
import com.gga.webhook.utils.ConverterUtil.Companion.toModel
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class EventServiceTest {

    @MockBean
    private lateinit var payloadRepository: PayloadRepository

    @MockBean
    private lateinit var issueRepository: IssueRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var assigneeRepository: AssigneeRepository

    @MockBean
    private lateinit var assigneesRepository: AssigneesRepository

    @MockBean
    private lateinit var labelRepository: LabelRepository

    @MockBean
    private lateinit var milestoneRepository: MilestoneRepository

    @MockBean
    private lateinit var creatorRepository: CreatorRepository

    @MockBean
    private lateinit var repositoryRepository: RepositoryRepository

    @MockBean
    private lateinit var licenseRepository: LicenseRepository

    @MockBean
    private lateinit var ownerRepository: OwnerRepository

    @MockBean
    private lateinit var senderRepository: SenderRepository

    private val eventService: EventService by lazy {
        EventService(
            payloadRepository,
            issueRepository,
            userRepository,
            assigneeRepository,
            assigneesRepository,
            labelRepository,
            milestoneRepository,
            creatorRepository,
            repositoryRepository,
            licenseRepository,
            ownerRepository,
            senderRepository
        )
    }

    private val builder: PayloadBuilder = PayloadBuilder()

    private val issueModel: IssueModel = (this.builder.payload().issue convertTo IssueModel::class.java).apply {
        this.assignee = null
        this.milestone = null
        this.user = null
        this.assignees = hashSetOf()
        this.labels = hashSetOf()
    }

    private val repositoryModel: RepositoryModel =
        (this.builder.payload().repository convertTo RepositoryModel::class.java).apply {
            this.owner = null
            this.license = null
        }

    private val payloadModel: PayloadModel =
        this.builder.payload().toModel().apply {
            this.sender = null
            this.repository = null
            this.issue = null
        }

    private val milestoneModel: MilestoneModel =
        (this.builder.milestoneDto() convertTo MilestoneModel::class.java).apply {
            this.creator = null
            this.issue = null
        }

    @Test
    @DisplayName("Payload -> Deve salvar o payload e retornar os objeto com as FKs configuradas")
    fun savePayload() {
        val payload: PayloadModel = this.builder.payload().toModel()

        val issue: IssueModel = payload.issue!!

        val assignee: AssigneeModel = issue.assignee!!

        val user: UserModel = issue.user!!

        val milestone: MilestoneModel = issue.milestone!!

        val creator: CreatorModel = milestone.creator!!

        val repository: RepositoryModel = payload.repository!!

        val license: LicenseModel = repository.license!!

        val owner: OwnerModel = repository.owner!!

        val sender: SenderModel = payload.sender!!

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(assignee)

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(user)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(milestone)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(creator)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(issue)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java))).thenReturn(license)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(owner)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(repository)

        `when`(this.senderRepository.save(any(SenderModel::class.java))).thenReturn(sender)

        `when`(this.payloadRepository.save(any(PayloadModel::class.java))).thenReturn(payload)

        val payloadDto: PayloadDto = this.eventService.savePayload(payload.toDto())

        assertEquals(payload.toDto(), payloadDto)
    }

    @Test
    @DisplayName("Issue -> Deve salvar a issue e retornar os objeto com as FKs configuradas")
    fun saveIssue() {
        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        val expectedUser: UserModel =
            (this.builder.userDto() convertTo UserModel::class.java).apply { this.issue = issueModel }

        val expectedAssignee: AssigneeModel =
            (this.builder.assigneeDto() convertTo AssigneeModel::class.java).apply { this.issue = issueModel }

        val expectedMilestone: MilestoneModel =
            (this.builder.milestoneDto() convertTo MilestoneModel::class.java).apply { this.issue = issueModel }

        val expectedCreator: CreatorModel =
            (this.builder.creatorDto() convertTo CreatorModel::class.java).apply { this.milestone = expectedMilestone }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(expectedUser)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(expectedAssignee)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(expectedMilestone)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        val expectedIssue: IssueModel = this.issueModel.apply {
            this.labels = expectedLabels
            this.assignees = expectedAssignees
            this.assignee = expectedAssignee
            this.user = expectedUser
            this.milestone = expectedMilestone
            this.payload = payloadModel
        }

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        val issueModel: IssueModel = this.eventService.saveIssue(issueModel, payloadModel)

        assertEquals(expectedIssue, issueModel)
    }

    @Test
    @DisplayName("Issue -> Deve salvar a issue (sem labels) e retornar os objeto com as FKs configuradas")
    fun saveIssueWithoutLabels() {
        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        val expectedUser: UserModel =
            (this.builder.userDto() convertTo UserModel::class.java).apply { this.issue = issueModel }

        val expectedAssignee: AssigneeModel =
            (this.builder.assigneeDto() convertTo AssigneeModel::class.java).apply { this.issue = issueModel }

        val expectedMilestone: MilestoneModel =
            (this.builder.milestoneDto() convertTo MilestoneModel::class.java).apply { this.issue = issueModel }

        val expectedCreator: CreatorModel =
            (this.builder.creatorDto() convertTo CreatorModel::class.java).apply { this.milestone = expectedMilestone }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(expectedUser)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(expectedAssignee)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(expectedMilestone)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        val expectedIssue: IssueModel = this.issueModel.apply {
            this.assignees = expectedAssignees
            this.assignee = expectedAssignee
            this.user = expectedUser
            this.milestone = expectedMilestone
            this.payload = payloadModel
        }

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        verify(this.labelRepository, never()).save(any(LabelsModel::class.java))

        val issueModel: IssueModel = this.eventService.saveIssue(issueModel, payloadModel)

        assertAll({
            assertEquals(expectedIssue, issueModel, "Must be equal to expected")
            assertTrue(expectedIssue.labels.isEmpty(), "Labels must be an empty Set")
        })
    }

    @Test
    @DisplayName("Issue -> Deve salvar a issue (sem assignee/assignees)e retornar os objeto com as FKs configuradas")
    fun saveIssueWithoutAssigneesAndAssignee() {
        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        val expectedUser: UserModel =
            (this.builder.userDto() convertTo UserModel::class.java).apply { this.issue = issueModel }

        val expectedMilestone: MilestoneModel =
            (this.builder.milestoneDto() convertTo MilestoneModel::class.java).apply { this.issue = issueModel }

        val expectedCreator: CreatorModel =
            (this.builder.creatorDto() convertTo CreatorModel::class.java).apply { this.milestone = expectedMilestone }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(expectedUser)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(expectedMilestone)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        val expectedIssue: IssueModel = this.issueModel.apply {
            this.labels = expectedLabels
            this.user = expectedUser
            this.milestone = expectedMilestone
            this.payload = payloadModel
        }

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        val issueModel: IssueModel = this.eventService.saveIssue(issueModel, payloadModel)

        verify(this.assigneeRepository, never()).save(any(AssigneeModel::class.java))

        verify(this.assigneesRepository, never()).save(any(AssigneesModel::class.java))

        assertAll({
            assertEquals(expectedIssue, issueModel, "Must be equal to expected")
            assertTrue(expectedIssue.assignees.isEmpty(), "Labels must be an empty Set")
            assertTrue(expectedIssue.assignee == null, "Assignee must be null")
        })
    }

    @Test
    @DisplayName("User -> Deve salvar o user e retornar os objeto com a FK configurada")
    fun saveUser() {
        val user: UserModel = this.builder.userDto() convertTo UserModel::class.java

        val expectedUser: UserModel =
            (this.builder.userDto() convertTo UserModel::class.java).apply { this.issue = issueModel }

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(expectedUser)

        val userModel: UserModel = this.eventService.saveUser(user, issueModel)

        assertEquals(expectedUser, userModel)
    }

    @Test
    @DisplayName("Assignee -> Deve salvar o assignee e retornar os objeto com a FK configurada")
    fun saveAssignee() {
        val assignee: AssigneeModel = this.builder.assigneeDto() convertTo AssigneeModel::class.java

        val expectedAssignee: AssigneeModel =
            (this.builder.assigneeDto() convertTo AssigneeModel::class.java).apply { this.issue = issueModel }

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(expectedAssignee)

        val assigneeModel: AssigneeModel = this.eventService.saveAssignee(assignee, issueModel)

        assertEquals(expectedAssignee, assigneeModel)
    }

    @Test
    @DisplayName("Assignees -> Deve salvar os assignees e retornar um Set com as FKs configuradas")
    fun saveAssignees() {
        val assignees: Set<AssigneesModel> = this.builder.assignees() convertTo AssigneesModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(expectedAssignees.toMutableList())

        val assigneesModel: HashSet<AssigneesModel> = this.eventService.saveAssignees(assignees, this.issueModel)

        assertEquals(expectedAssignees, assigneesModel)
    }

    @Test
    @DisplayName("Labels -> Deve salvar as labels e retornar um Set com as FKs configuradas")
    fun saveLabels() {
        val labels: Set<LabelsModel> = this.builder.labels() convertTo LabelsModel::class.java

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        `when`(this.labelRepository.saveAll(anySet())).thenReturn(expectedLabels.toMutableList())

        val labelsModel: HashSet<LabelsModel> = this.eventService.saveLabels(labels, this.issueModel)

        assertEquals(expectedLabels, labelsModel)
    }

    @Test
    @DisplayName("Milestone -> Deve salvar o milestone e retornar os objeto com a FK configurada")
    fun saveMilestone() {
        val expectedCreator: CreatorModel =
            (this.builder.creatorDto() convertTo CreatorModel::class.java).apply { this.milestone = milestoneModel }

        val expectedMilestone: MilestoneModel = milestoneModel.apply {
            this.creator = expectedCreator
            this.issue = issueModel
        }

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(expectedMilestone)

        val milestoneModel: MilestoneModel = this.eventService.saveMilestone(this.milestoneModel, this.issueModel)

        assertEquals(expectedMilestone, milestoneModel)
    }

    @Test
    @DisplayName("Creator -> Deve salvar o creator e retornar os objeto com a FK configurada")
    fun saveCreator() {
        val creator: CreatorModel = this.builder.creatorDto() convertTo CreatorModel::class.java

        val expectedCreator: CreatorModel =
            (this.builder.creatorDto() convertTo CreatorModel::class.java).apply { this.milestone = milestoneModel }

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        val creatorModel: CreatorModel = this.eventService.saveCreator(creator, this.milestoneModel)

        assertEquals(expectedCreator, creatorModel)
    }

    @Test
    @DisplayName("Repository -> Deve salvar o repository e retornar os objeto com a FK configurada")
    fun saveRepository() {
        val expectedOwner: OwnerModel =
            (this.builder.ownerDto() convertTo OwnerModel::class.java).apply { this.repository = repositoryModel }

        val expectedLicense: LicenseModel =
            (this.builder.licenseDto() convertTo LicenseModel::class.java).apply { this.repository = repositoryModel }

        val repository: RepositoryModel = this.repositoryModel

        val expectedRepository: RepositoryModel = this.repositoryModel.apply {
            this.owner = expectedOwner
            this.license = expectedLicense
            this.payload = payloadModel
        }

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(expectedOwner)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java))).thenReturn(expectedLicense)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(expectedRepository)

        val repositoryModel: RepositoryModel = this.eventService.saveRepository(repository, this.payloadModel)

        assertEquals(expectedRepository, repositoryModel)
    }

    @Test
    @DisplayName("Repository -> Deve salvar o repository (sem license) e retornar os objeto com a FK configurada")
    fun saveRepositoryWithoutLicense() {
        val expectedOwner: OwnerModel =
            (this.builder.ownerDto() convertTo OwnerModel::class.java).apply { this.repository = repositoryModel }

        val repository: RepositoryModel = this.repositoryModel

        val expectedRepository: RepositoryModel = this.repositoryModel.apply {
            this.owner = expectedOwner
            this.payload = payloadModel
        }

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(expectedOwner)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(expectedRepository)

        val repositoryModel: RepositoryModel = this.eventService.saveRepository(repository, this.payloadModel)

        verify(this.licenseRepository, never()).save(any(LicenseModel::class.java))

        assertEquals(expectedRepository, repositoryModel)
    }

    @Test
    @DisplayName("License -> Deve salvar a license e retornar os objeto com a FK configurada")
    fun saveLicense() {
        val license: LicenseModel = this.builder.licenseDto() convertTo LicenseModel::class.java

        val expectedLicense: LicenseModel =
            (this.builder.licenseDto() convertTo LicenseModel::class.java).apply { this.repository = repositoryModel }

        `when`(this.licenseRepository.save(any(LicenseModel::class.java))).thenReturn(expectedLicense)

        val licenseModel: LicenseModel = this.eventService.saveLicense(license, this.repositoryModel)

        assertEquals(expectedLicense, licenseModel)
    }

    @Test
    @DisplayName("Owner -> Deve salvar o owner e retornar os objeto com a FK configurada")
    fun saveOwner() {
        val owner: OwnerModel = this.builder.ownerDto() convertTo OwnerModel::class.java

        val expectedOwner: OwnerModel =
            (this.builder.ownerDto() convertTo OwnerModel::class.java).apply { this.repository = repositoryModel }

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(expectedOwner)

        val ownerModel: OwnerModel = this.eventService.saveOwner(owner, this.repositoryModel)

        assertEquals(expectedOwner, ownerModel)
    }

    @Test
    @DisplayName("Sender -> Deve salvar o sender e retornar os objeto com a FK configurada")
    fun saveSender() {
        val sender: SenderModel = this.builder.senderDto() convertTo SenderModel::class.java

        val expectedSender: SenderModel =
            (this.builder.senderDto() convertTo SenderModel::class.java).apply { this.payload = payloadModel }

        `when`(this.senderRepository.save(any(SenderModel::class.java))).thenReturn(expectedSender)

        val senderModel: SenderModel = this.eventService.saveSender(sender, this.payloadModel)

        assertEquals(expectedSender, senderModel)
    }

    @Test
    @DisplayName("Deve retornar a issue com o número solicitado")
    fun getIssueByNumber() {

    }

}
