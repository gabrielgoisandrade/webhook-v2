package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.repositories.IssueRepository
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.impls.IssueServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class IssueServiceImplTest {

    @MockBean
    private lateinit var issueRepository: IssueRepository

    @MockBean
    private lateinit var assigneesRepository: AssigneesRepository

    @MockBean
    private lateinit var labelsRepository: LabelsRepository

    private val issueServiceImpl: IssueServiceImpl by lazy {
        IssueServiceImpl(issueRepository, assigneesRepository, labelsRepository)
    }

    private val builder: PayloadBuilder = PayloadBuilder()

    private val issueDto: IssueDto = this.builder.payload().issue!!

    @Test
    @DisplayName("Deve retornar a issue com o número solicitado")
    fun getIssueByNumber() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        val expectedIssue: IssueDto = this.issueDto.apply {
            this.assignees = expectedAssignees convertTo AssigneesDto::class.java
            this.labels = expectedLabels convertTo LabelsDto::class.java
        }

        `when`(this.labelsRepository.getLabels()).thenReturn(expectedLabels)

        `when`(this.assigneesRepository.getAssignees()).thenReturn(expectedAssignees)

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenReturn(hashSetOf(this.issueDto convertTo IssueModel::class.java))

        org.junit.jupiter.api.Assertions.assertDoesNotThrow { this.issueServiceImpl.getIssueByNumber(Random.nextInt()) }

        val issueByNumber: HashSet<IssueDto> = this.issueServiceImpl.getIssueByNumber(Random.nextInt())

        issueByNumber.forEach {
            assertAll({
                org.junit.jupiter.api.Assertions.assertEquals(expectedIssue, it)
                org.junit.jupiter.api.Assertions.assertEquals(
                    expectedIssue.assignees,
                    it.assignees,
                    "Assignees must be equal to expected"
                )
                org.junit.jupiter.api.Assertions.assertEquals(
                    expectedIssue.labels,
                    it.labels,
                    "Labels must be equal to expected"
                )
            })
        }
    }

    @Test
    @DisplayName("Deve retornar a issue (sem assignees) com o número solicitado")
    fun getIssueByNumberWithoutAssignees() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedLabels: HashSet<LabelsModel> = (this.builder.labels() convertTo LabelsModel::class.java).map {
            it.apply { this.issue = issueModel }
        }.toHashSet()

        val expectedIssue: IssueDto = this.issueDto.apply {
            this.assignees = hashSetOf()
            this.labels = expectedLabels convertTo LabelsDto::class.java
        }

        `when`(this.labelsRepository.getLabels()).thenReturn(expectedLabels)

        `when`(this.assigneesRepository.getAssignees()).thenReturn(hashSetOf())

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenReturn(hashSetOf(this.issueDto convertTo IssueModel::class.java))

        org.junit.jupiter.api.Assertions.assertDoesNotThrow { this.issueServiceImpl.getIssueByNumber(Random.nextInt()) }

        val issueByNumber: HashSet<IssueDto> = this.issueServiceImpl.getIssueByNumber(Random.nextInt())

        issueByNumber.forEach {
            assertAll({
                org.junit.jupiter.api.Assertions.assertEquals(expectedIssue, it)
                org.junit.jupiter.api.Assertions.assertEquals(
                    expectedIssue.labels,
                    it.labels,
                    "Labels must be equal to expected"
                )
                org.junit.jupiter.api.Assertions.assertTrue(it.assignees.isEmpty(), "Assignees must be an empty Set")
            })
        }
    }

    @Test
    @DisplayName("Deve retornar a issue (sem a label) com o número solicitado")
    fun getIssueByNumberWithoutLabels() {
        val issueModel: IssueModel = issueDto convertTo IssueModel::class.java

        val expectedAssignees: HashSet<AssigneesModel> =
            (this.builder.assignees() convertTo AssigneesModel::class.java).map {
                it.apply { this.issue = issueModel }
            }.toHashSet()

        val expectedIssue: IssueDto = this.issueDto.apply {
            this.assignees = expectedAssignees convertTo AssigneesDto::class.java
            this.labels = hashSetOf()
        }

        `when`(this.labelsRepository.getLabels()).thenReturn(hashSetOf())

        `when`(this.assigneesRepository.getAssignees()).thenReturn(expectedAssignees)

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenReturn(hashSetOf(this.issueDto convertTo IssueModel::class.java))

        org.junit.jupiter.api.Assertions.assertDoesNotThrow { this.issueServiceImpl.getIssueByNumber(Random.nextInt()) }

        val issueByNumber: HashSet<IssueDto> = this.issueServiceImpl.getIssueByNumber(Random.nextInt())

        issueByNumber.forEach {
            assertAll({
                org.junit.jupiter.api.Assertions.assertEquals(expectedIssue, it)
                org.junit.jupiter.api.Assertions.assertTrue(it.labels.isEmpty(), "Labels must be an empty Set")
                org.junit.jupiter.api.Assertions.assertEquals(
                    expectedIssue.assignees,
                    it.assignees,
                    "Assignees must be equal to expected"
                )
            })
        }
    }

    @Test
    @DisplayName("Deve retornar a issue (sem assignees e labels) com o número solicitado")
    fun getIssueByNumberWithoutAssigneesAndLabels() {
        val expectedIssue: IssueDto = this.issueDto.apply {
            this.assignees = hashSetOf()
            this.labels = hashSetOf()
        }

        `when`(this.labelsRepository.getLabels()).thenReturn(hashSetOf())

        `when`(this.assigneesRepository.getAssignees()).thenReturn(hashSetOf())

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenReturn(hashSetOf(this.issueDto convertTo IssueModel::class.java))

        org.junit.jupiter.api.Assertions.assertDoesNotThrow { this.issueServiceImpl.getIssueByNumber(Random.nextInt()) }

        val issueByNumber: HashSet<IssueDto> = this.issueServiceImpl.getIssueByNumber(Random.nextInt())

        issueByNumber.forEach {
            assertAll({
                org.junit.jupiter.api.Assertions.assertEquals(expectedIssue, it)
                org.junit.jupiter.api.Assertions.assertTrue(it.assignees.isEmpty(), "Assignees must be an empty Set")
                org.junit.jupiter.api.Assertions.assertTrue(it.labels.isEmpty(), "Labels must be an empty Set")
            })
        }
    }

    @Test
    @DisplayName("Deve retornar um erro caso não exista uma issue com o número solicitado")
    fun throwErrorByIssueNotFound() {
        val number: Int = Random.nextInt()

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenThrow(IssueNotFoundException("Issue #$number not found"))

        assertThrows<IssueNotFoundException> { this.issueServiceImpl.getIssueByNumber(number) }.also {
            assertThat(it).isInstanceOf(IssueNotFoundException::class.java)
                .hasMessage("Issue #$number not found")
        }
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhuma issue de determinado payload")
    fun throwErrorByNoIssueFound() {
        `when`(this.issueRepository.getIssue()).thenThrow(IssueNotFoundException("No issue found."))

        assertThrows<IssueNotFoundException> { this.issueServiceImpl.getIssue() }.also {
            assertThat(it).isInstanceOf(IssueNotFoundException::class.java).hasMessage("No issue found.")
        }
    }

}