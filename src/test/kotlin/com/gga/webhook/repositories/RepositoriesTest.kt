package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.*
import com.gga.webhook.models.dto.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class RepositoriesTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var payloadRepository: PayloadRepository

    @Autowired
    private lateinit var issueRepository: IssueRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var assigneeRepository: AssigneeRepository

    @Autowired
    private lateinit var assigneesRepository: AssigneesRepository

    @Autowired
    private lateinit var labelsRepository: LabelsRepository

    @Autowired
    private lateinit var milestoneRepository: MilestoneRepository

    @Autowired
    private lateinit var creatorRepository: CreatorRepository

    @Autowired
    private lateinit var repositoryRepository: RepositoryRepository

    @Autowired
    private lateinit var licenseRepository: LicenseRepository

    @Autowired
    private lateinit var ownerRepository: OwnerRepository

    @Autowired
    private lateinit var senderRepository: SenderRepository

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payloadDto: PayloadDto = this.builder.payload()

    private val issueDto: IssueDto = this.builder.issue()

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
    @DisplayName("Deve persistir Payload no database")
    fun savePayload() {
        val repositoryModel: RepositoryModel = (this.repositoryDto convertTo RepositoryModel::class.java).apply {
            this.license = entityManager.merge(licenseDto convertTo LicenseModel::class.java)
            this.owner = entityManager.persist(ownerDto convertTo OwnerModel::class.java)
        }

        val milestoneModel: MilestoneModel = (this.milestoneDto convertTo MilestoneModel::class.java).apply {
            this.creator = entityManager.persist(creatorDto convertTo CreatorModel::class.java)
        }

        val issueModel: IssueModel = (this.issueDto convertTo IssueModel::class.java).apply {
            this.assignee = entityManager.persist(assigneeDto convertTo AssigneeModel::class.java)
            this.milestone = entityManager.persist(milestoneModel)
            this.user = entityManager.persist(userDto convertTo UserModel::class.java)
        }

        val toSave: PayloadModel = (this.payloadDto convertTo PayloadModel::class.java).apply {
            this.issue = entityManager.persist(issueModel)
            this.repository = entityManager.persist(repositoryModel)
            this.sender = entityManager.persist(senderDto convertTo SenderModel::class.java)
        }

        this.entityManager.persist(toSave)

        this.payloadRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Issue no database")
    fun saveIssue() {
        val milestoneModel: MilestoneModel = (this.milestoneDto convertTo MilestoneModel::class.java).apply {
            this.creator = entityManager.persist(creatorDto convertTo CreatorModel::class.java)
        }

        val toSave: IssueModel = (this.issueDto convertTo IssueModel::class.java).apply {
            this.assignee = entityManager.persist(assigneeDto convertTo AssigneeModel::class.java)
            this.milestone = entityManager.persist(milestoneModel)
            this.user = entityManager.persist(userDto convertTo UserModel::class.java)
        }

        this.entityManager.persist(toSave)

        this.issueRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Labels no database")
    fun saveLabels() {
        val issueToSave: IssueModel = this.entityManager.persist(this.issueDto convertTo IssueModel::class.java)

        val toSave: HashSet<LabelsModel> = (this.labelsDto convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueToSave }
        }.toHashSet()

        toSave.forEach { this.entityManager.persist(it) }

        this.labelsRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Assignees no database")
    fun saveAssignees() {
        val issueToSave: IssueModel = this.entityManager.persist(this.issueDto convertTo IssueModel::class.java)

        val toSave: HashSet<AssigneesModel> = (this.assigneesDto convertTo AssigneesModel::class.java).map {
            it.apply { this.issue = issueToSave }
        }.toHashSet()

        toSave.forEach { this.entityManager.persist(it) }

        this.assigneesRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Assignee no database")
    fun saveAssignee() {
        this.entityManager.persist(this.assigneeDto convertTo AssigneeModel::class.java)

        this.assigneeRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir User no database")
    fun saveUser() {
        this.entityManager.persist(this.userDto convertTo UserModel::class.java)

        this.userRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Milestone no database")
    fun saveMilestone() {
        val toSave: MilestoneModel = (this.milestoneDto convertTo MilestoneModel::class.java).apply {
            this.creator = entityManager.persist(creatorDto convertTo CreatorModel::class.java)
        }

        this.entityManager.persist(toSave)

        this.milestoneRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Creator no database")
    fun saveCreator() {
        this.entityManager.persist(this.creatorDto convertTo CreatorModel::class.java)

        this.creatorRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Repository no database")
    fun saveRepository() {
        val toSave: RepositoryModel = (this.repositoryDto convertTo RepositoryModel::class.java).apply {
            this.license = entityManager.merge(licenseDto convertTo LicenseModel::class.java)
        }

        this.entityManager.persist(toSave)

        this.repositoryRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir License no database")
    fun saveLicense() {
        this.entityManager.merge(this.licenseDto convertTo LicenseModel::class.java)

        this.licenseRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Owner no database")
    fun saveOwner() {
        this.entityManager.persist(this.ownerDto convertTo OwnerModel::class.java)

        this.ownerRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Sender no database")
    fun saveSender() {
        this.entityManager.persist(this.senderDto convertTo SenderModel::class.java)

        this.senderRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

}
