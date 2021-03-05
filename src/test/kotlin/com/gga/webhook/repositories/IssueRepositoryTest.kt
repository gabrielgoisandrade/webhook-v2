package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.*
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
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
internal class IssueRepositoryTest {

    @Autowired
    private lateinit var issueRepository: IssueRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payload: PayloadDto = this.builder.payload()

    private val issueDto: IssueDto = this.builder.issue()

    private val userDto: UserDto = this.builder.userDto()

    private val assigneeDto: AssigneeDto = this.builder.assigneeDto()

    private val milestoneDto: MilestoneDto = this.builder.milestoneDto()

    private val creatorDto: CreatorDto = this.builder.creatorDto()

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
    @DisplayName("Deve retornar a issue de determinado payload")
    fun getIssue() {
        val persist: PayloadModel = this.entityManager.persist((this.payload convertTo PayloadModel::class.java).apply {
            this.repository!!.license = entityManager.merge(builder.licenseDto() convertTo LicenseModel::class.java)
        })

        val issue: IssueModel = this.issueRepository.getIssue()!!

        assertEquals(persist.issue, issue)
    }

}