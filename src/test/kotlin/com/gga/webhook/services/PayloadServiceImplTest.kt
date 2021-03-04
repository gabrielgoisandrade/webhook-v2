package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
import com.gga.webhook.repositories.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class PayloadServiceImplTest {

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
    private lateinit var labelsRepository: LabelsRepository

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

    private val payloadServiceImpl: PayloadServiceImpl by lazy {
        PayloadServiceImpl(
            payloadRepository,
            issueRepository,
            userRepository,
            assigneeRepository,
            assigneesRepository,
            labelsRepository,
            milestoneRepository,
            creatorRepository,
            repositoryRepository,
            licenseRepository,
            ownerRepository,
            senderRepository
        )
    }

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payloadDto: PayloadDto = this.builder.payload()

    private val issueDto: IssueDto = this.builder.payload().issue!!

    private val userDto: UserDto = this.builder.userDto()

    private val labelsDto: HashSet<LabelsDto> = this.builder.labels()

    private val assigneesDto: HashSet<AssigneesDto> = this.builder.assignees()

    private val assigneeDto: AssigneeDto = this.builder.assigneeDto()

    private val milestoneDto: MilestoneDto = this.builder.milestoneDto()

    private val creatorDto: CreatorDto = this.builder.creatorDto()

    private val repositoryDto: RepositoryDto = this.builder.repositoryDto()

    private val licenseDto: LicenseDto = this.builder.licenseDto()

    private val ownerDto: OwnerDto = this.builder.ownerDto()

    private val senderDto: SenderDto = this.builder.senderDto()

    @Test
    @DisplayName("Payload -> Deve salvar o payload e retornar os objeto com as FKs configuradas")
    fun savePayload() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(expectedAssignees.toMutableList())

        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(expectedLabels.toMutableList())

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(this.assigneeDto convertTo AssigneeModel::class.java)

        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(this.userDto convertTo UserModel::class.java)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java)))
            .thenReturn(this.milestoneDto convertTo MilestoneModel::class.java)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java)))
            .thenReturn(this.creatorDto convertTo CreatorModel::class.java)

        `when`(this.issueRepository.save(any(IssueModel::class.java)))
            .thenReturn(this.issueDto convertTo IssueModel::class.java)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java)))
            .thenReturn(this.licenseDto convertTo LicenseModel::class.java)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java)))
            .thenReturn(this.ownerDto convertTo OwnerModel::class.java)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java)))
            .thenReturn(this.repositoryDto convertTo RepositoryModel::class.java)

        `when`(this.senderRepository.save(any(SenderModel::class.java)))
            .thenReturn(this.senderDto convertTo SenderModel::class.java)

        `when`(this.payloadRepository.save(any(PayloadModel::class.java)))
            .thenReturn(this.payloadDto convertTo PayloadModel::class.java)

        val payloadDto: PayloadDto = this.payloadServiceImpl.savePayload(this.payloadDto)

        assertAll({
            assertEquals(this.payloadDto, payloadDto)
            assertEquals(this.payloadDto.issue, payloadDto.issue)
            assertEquals(this.payloadDto.repository, payloadDto.repository)
            assertEquals(this.payloadDto.sender, payloadDto.sender)
        })
    }

    @Test
    @DisplayName("Payload -> Deve salvar o payload (sem labels em issue) e retornar os objeto com as FKs configuradas")
    fun savePayloadWithoutLabelsAtIssue() {
        this.payloadDto.apply { this.issue!!.labels = hashSetOf() }

        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(expectedAssignees.toMutableList())

        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(mutableListOf())

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(this.assigneeDto convertTo AssigneeModel::class.java)

        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(this.userDto convertTo UserModel::class.java)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java)))
            .thenReturn(this.milestoneDto convertTo MilestoneModel::class.java)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java)))
            .thenReturn(this.creatorDto convertTo CreatorModel::class.java)

        `when`(this.issueRepository.save(any(IssueModel::class.java)))
            .thenReturn(this.issueDto convertTo IssueModel::class.java)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java)))
            .thenReturn(this.licenseDto convertTo LicenseModel::class.java)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java)))
            .thenReturn(this.ownerDto convertTo OwnerModel::class.java)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java)))
            .thenReturn(this.repositoryDto convertTo RepositoryModel::class.java)

        `when`(this.senderRepository.save(any(SenderModel::class.java)))
            .thenReturn(this.senderDto convertTo SenderModel::class.java)

        `when`(this.payloadRepository.save(any(PayloadModel::class.java)))
            .thenReturn(this.payloadDto convertTo PayloadModel::class.java)

        val payloadDto: PayloadDto = this.payloadServiceImpl.savePayload(this.payloadDto)

        assertAll({
            assertEquals(this.payloadDto, payloadDto)
            assertEquals(this.payloadDto.issue, payloadDto.issue)
            assertEquals(this.payloadDto.repository, payloadDto.repository)
            assertEquals(this.payloadDto.sender, payloadDto.sender)
        })
    }

    @Test
    @DisplayName("Payload -> Deve salvar o payload (sem assignees em issue) e retornar os objeto com as FKs configuradas")
    fun savePayloadWithoutAssigneesAtIssue() {
        this.payloadDto.apply { this.issue!!.assignees = hashSetOf() }

        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(mutableListOf())

        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(expectedLabels.toMutableList())

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(this.assigneeDto convertTo AssigneeModel::class.java)

        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(this.userDto convertTo UserModel::class.java)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java)))
            .thenReturn(this.milestoneDto convertTo MilestoneModel::class.java)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java)))
            .thenReturn(this.creatorDto convertTo CreatorModel::class.java)

        `when`(this.issueRepository.save(any(IssueModel::class.java)))
            .thenReturn(this.issueDto convertTo IssueModel::class.java)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java)))
            .thenReturn(this.licenseDto convertTo LicenseModel::class.java)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java)))
            .thenReturn(this.ownerDto convertTo OwnerModel::class.java)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java)))
            .thenReturn(this.repositoryDto convertTo RepositoryModel::class.java)

        `when`(this.senderRepository.save(any(SenderModel::class.java)))
            .thenReturn(this.senderDto convertTo SenderModel::class.java)

        `when`(this.payloadRepository.save(any(PayloadModel::class.java)))
            .thenReturn(this.payloadDto convertTo PayloadModel::class.java)

        val payloadDto: PayloadDto = this.payloadServiceImpl.savePayload(this.payloadDto)

        assertAll({
            assertEquals(this.payloadDto, payloadDto)
            assertEquals(this.payloadDto.issue, payloadDto.issue)
            assertEquals(this.payloadDto.repository, payloadDto.repository)
            assertEquals(this.payloadDto.sender, payloadDto.sender)
        })
    }

    @Test
    @DisplayName("Issue -> Deve salvar a issue e retornar os objeto com as FKs configuradas")
    fun saveIssue() {
        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(this.userDto convertTo UserModel::class.java)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(this.assigneeDto convertTo AssigneeModel::class.java)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java)))
            .thenReturn(this.creatorDto convertTo CreatorModel::class.java)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java)))
            .thenReturn(this.milestoneDto convertTo MilestoneModel::class.java)

        val expectedIssue: IssueModel = this.issueDto convertTo IssueModel::class.java

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        val issueModel: IssueModel = this.payloadServiceImpl.saveIssue(this.issueDto)

        assertAll({
            assertEquals(expectedIssue, issueModel)
            assertEquals(expectedIssue.user, issueModel.user, "Fk must be equal User")
            assertEquals(expectedIssue.assignee, issueModel.assignee, "Fk must be equal Assignee")
            assertEquals(expectedIssue.milestone, issueModel.milestone, "Fk must be equal Milestone")
            assertFalse(expectedIssue.user == null, "User must not be null")
            assertFalse(expectedIssue.assignee == null, "Assignee must not be null")
            assertFalse(expectedIssue.milestone == null, "Milestone must not be null")
        })
    }

    @Test
    @DisplayName("Issue -> Deve salvar a issue (sem milestone) e retornar os objeto com as FKs configuradas")
    fun saveIssueWithoutMilestone() {
        this.issueDto.apply { this.milestone = null }

        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(this.userDto convertTo UserModel::class.java)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(this.assigneeDto convertTo AssigneeModel::class.java)

        val expectedIssue: IssueModel = this.issueDto convertTo IssueModel::class.java

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(expectedIssue)

        val issueModel: IssueModel = this.payloadServiceImpl.saveIssue(this.issueDto)

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))

        assertAll({
            assertEquals(expectedIssue, issueModel)
            assertEquals(expectedIssue.user, issueModel.user, "Fk must be equal User")
            assertEquals(expectedIssue.assignee, issueModel.assignee, "Fk must be equal Assignee")
            assertFalse(expectedIssue.user == null, "User must not be null")
            assertFalse(expectedIssue.assignee == null, "Assignee must not be null")
            assertTrue(expectedIssue.milestone == null, "Milestone must be null")
        })
    }

    @Test
    @DisplayName("User -> Deve salvar o user e retornar o objeto salvo")
    fun saveUser() {
        val expectedUser: UserModel = this.userDto convertTo UserModel::class.java

        `when`(this.userRepository.save(any(UserModel::class.java)))
            .thenReturn(expectedUser)

        val userModel: UserModel = this.payloadServiceImpl.saveUser(this.userDto)

        assertEquals(expectedUser, userModel)
    }

    @Test
    @DisplayName("Assignee -> Deve salvar o assignee e retornar os objeto com a FK configurada")
    fun saveAssignee() {
        val expectedAssignee: AssigneeModel = this.assigneeDto convertTo AssigneeModel::class.java

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java)))
            .thenReturn(expectedAssignee)

        val assigneeModel: AssigneeModel = this.payloadServiceImpl.saveAssignee(this.assigneeDto)

        assertEquals(expectedAssignee, assigneeModel)
    }

    @Test
    @DisplayName("Assignees -> Deve salvar os assignees e retornar um Set com as FKs configuradas")
    fun saveAssignees() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        `when`(this.assigneesRepository.saveAll(anySet())).thenReturn(expectedAssignees.toMutableList())

        val assigneesModel: HashSet<AssigneesModel> =
            this.payloadServiceImpl.saveAssignees(this.assigneesDto, issueModel)

        assertAll({
            assertEquals(expectedAssignees, assigneesModel)
            expectedAssignees.forEach { assertEquals(issueModel, it.issue, "FK must be equal to Issue") }
        })
    }

    @Test
    @DisplayName("Labels -> Deve salvar as labels e retornar um Set com as FKs configuradas")
    fun saveLabels() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        `when`(this.labelsRepository.saveAll(anySet())).thenReturn(expectedLabels.toMutableList())

        val labelsModel: HashSet<LabelsModel> = this.payloadServiceImpl.saveLabels(this.labelsDto, issueModel)

        assertAll({
            assertEquals(expectedLabels, labelsModel)
            expectedLabels.forEach { assertEquals(issueModel, it.issue, "FK must be equal to Issue") }
        })
    }

    @Test
    @DisplayName("Milestone -> Deve salvar o milestone e retornar o objeto salvo")
    fun saveMilestone() {
        val expectedMilestone: MilestoneModel = this.milestoneDto convertTo MilestoneModel::class.java

        `when`(this.creatorRepository.save(any(CreatorModel::class.java)))
            .thenReturn(this.creatorDto convertTo CreatorModel::class.java)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java)))
            .thenReturn(this.milestoneDto convertTo MilestoneModel::class.java)

        val milestoneModel: MilestoneModel = this.payloadServiceImpl.saveMilestone(this.milestoneDto)

        assertAll({
            assertEquals(expectedMilestone, milestoneModel)
            assertFalse(expectedMilestone.creator == null, "Creator must not be null")
        })
    }

    @Test
    @DisplayName("Creator -> Deve salvar o creator e retornar o objeto salvo")
    fun saveCreator() {
        val expectedCreator: CreatorModel = (this.builder.creatorDto() convertTo CreatorModel::class.java)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(expectedCreator)

        val creatorModel: CreatorModel = this.payloadServiceImpl.saveCreator(this.creatorDto)

        assertEquals(expectedCreator, creatorModel)
    }

    @Test
    @DisplayName("Repository -> Deve salvar o repository e retornar o objeto salvo")
    fun saveRepository() {
        `when`(this.ownerRepository.save(any(OwnerModel::class.java)))
            .thenReturn(this.ownerDto convertTo OwnerModel::class.java)

        `when`(this.licenseRepository.save(any(LicenseModel::class.java)))
            .thenReturn(this.licenseDto convertTo LicenseModel::class.java)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java)))
            .thenReturn(this.repositoryDto convertTo RepositoryModel::class.java)

        val expectedRepository: RepositoryModel = this.repositoryDto convertTo RepositoryModel::class.java

        val repositoryModel: RepositoryModel = this.payloadServiceImpl.saveRepository(this.repositoryDto)

        assertAll({
            assertEquals(expectedRepository, repositoryModel)
            assertFalse(expectedRepository.license == null, "License must not be null")
            assertFalse(expectedRepository.owner == null, "Owner must not be null")
        })
    }

    @Test
    @DisplayName("Repository -> Deve salvar o repository (sem license) e retornar o objeto salvo")
    fun saveRepositoryWithoutLicense() {
        this.repositoryDto.apply { this.license = null }

        val ownerModel: OwnerModel = ownerDto convertTo OwnerModel::class.java

        val expectedRepository: RepositoryModel =
            (this.repositoryDto convertTo RepositoryModel::class.java)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(ownerModel)

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(expectedRepository)

        val repositoryModel: RepositoryModel = this.payloadServiceImpl.saveRepository(this.repositoryDto)

        verify(this.licenseRepository, never()).save(any(LicenseModel::class.java))

        assertAll({
            assertEquals(expectedRepository, repositoryModel)
            assertTrue(expectedRepository.license == null, "License must be null")
        })
    }

    @Test
    @DisplayName("License -> Deve salvar a license e retornar o objeto salvo")
    fun saveLicense() {
        val expectedLicense = this.licenseDto convertTo LicenseModel::class.java

        `when`(this.licenseRepository.save(any(LicenseModel::class.java)))
            .thenReturn(this.licenseDto convertTo LicenseModel::class.java)

        val licenseModel: LicenseModel = this.payloadServiceImpl.saveLicense(this.licenseDto)

        assertEquals(expectedLicense, licenseModel)
    }

    @Test
    @DisplayName("Owner -> Deve salvar o owner e retornar o objeto salvo")
    fun saveOwner() {
        val expectedOwner: OwnerModel = this.ownerDto convertTo OwnerModel::class.java

        `when`(this.ownerRepository.save(any(OwnerModel::class.java)))
            .thenReturn(this.ownerDto convertTo OwnerModel::class.java)

        val ownerModel: OwnerModel = this.payloadServiceImpl.saveOwner(this.ownerDto)

        assertEquals(expectedOwner, ownerModel)
    }

    @Test
    @DisplayName("Sender -> Deve salvar o sender e retornar o objeto salvo")
    fun saveSender() {
        val expectedSender: SenderModel = this.senderDto convertTo SenderModel::class.java

        `when`(this.senderRepository.save(any(SenderModel::class.java)))
            .thenReturn(this.senderDto convertTo SenderModel::class.java)

        val senderModel: SenderModel = this.payloadServiceImpl.saveSender(this.senderDto)

        assertEquals(expectedSender, senderModel)
    }

}
