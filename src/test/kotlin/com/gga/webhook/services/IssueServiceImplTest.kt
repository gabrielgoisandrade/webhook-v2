package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.vO.IssueVo
import com.gga.webhook.repositories.AssigneesRepository
import com.gga.webhook.repositories.IssueRepository
import com.gga.webhook.repositories.LabelsRepository
import com.gga.webhook.services.impls.IssueServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        val expectedIssue: HashSet<IssueVo> = hashSetOf(this.issueDto convertTo IssueVo::class.java)

        `when`(this.issueRepository.findIssueModelByNumber(anyInt()))
            .thenReturn(hashSetOf(this.issueDto convertTo IssueModel::class.java))

        val issueByNumber: HashSet<IssueVo> = this.issueServiceImpl.getIssueByNumber(anyInt())

        assertEquals(expectedIssue, issueByNumber)
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