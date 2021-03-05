package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
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
    private lateinit var assigneesRepository: AssigneesRepository

    @Autowired
    private lateinit var labelsRepository: LabelsRepository

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
    @DisplayName("Deve persistir Labels no database")
    fun saveLabels() {
        val issueToSave: IssueModel = this.entityManager.persist(this.issueDto convertTo IssueModel::class.java)

        val toSave: HashSet<LabelsModel> = (this.labelsDto convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueToSave }
        }.toHashSet()

        toSave.forEach { this.entityManager.merge(it) }

        this.labelsRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve persistir Assignees no database")
    fun saveAssignees() {
        val issueToSave: IssueModel = this.entityManager.persist(this.issueDto convertTo IssueModel::class.java)

        val toSave: HashSet<AssigneesModel> = (this.assigneesDto convertTo AssigneesModel::class.java).map {
            it.apply { this.issue = issueToSave }
        }.toHashSet()

        toSave.forEach { this.entityManager.merge(it) }

        this.assigneesRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

}
