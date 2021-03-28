package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.dTO.LabelsDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.*

internal class IssueServiceImplTest : BaseServiceImplTestFactory() {

    private val service: IssueServiceImpl by lazy {
        IssueServiceImpl(
            this.issueRepository,
            this.labelsRepository,
            this.assigneesRepository
        )
    }

    private val expectedModel: IssueModel = this.model.issue

    private val expectedLabelsModel: List<LabelsModel> = listOf(this.model.labels)

    private val expectedAssigneesModel: List<AssigneesModel> = listOf(this.model.assignees)

    private val expectedLabelsDto: List<LabelsDto> = this.dto.labels()

    private val expectedAssigneesDto: List<AssigneesDto> = this.dto.assignees()

    private val expectedDto: IssueDto = this.dto.issueDto().apply {
        this.assignees = emptyList()
        this.labels = emptyList()
    }

    @Test
    fun saveIssue() {
        `when`(this.issueRepository.findByNumber(anyInt())).thenReturn(Optional.empty())

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveIssue(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingIssue() {
        `when`(this.issueRepository.findByNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveIssue(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }

        verify(this.issueRepository, never()).save(any(IssueModel::class.java))
    }

    @Test
    fun findIssueByNumber() {
        `when`(this.issueRepository.findByNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        `when`(this.labelsRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedLabelsModel))

        `when`(this.assigneesRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedAssigneesModel))

        this.service.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it).isEqualTo(this.expectedDto.apply {
                this.assignees = expectedAssigneesDto
                this.labels = expectedLabelsDto
            })
        }
    }

    @Test
    fun findIssueByEventAction() {
        `when`(this.issueRepository.findByEventAction(anyString())).thenReturn(Optional.of(this.expectedModel))

        `when`(this.labelsRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedLabelsModel))

        `when`(this.assigneesRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedAssigneesModel))

        this.service.findIssueByEventAction(EVENT_ACTION).also {
            assertThat(it).isEqualTo(this.expectedDto.apply {
                this.assignees = expectedAssigneesDto
                this.labels = expectedLabelsDto
            })
        }
    }

    @Test
    fun throwErrorByEventActionNotFound() {
        `when`(this.issueRepository.findByEventAction(anyString()))
            .thenThrow(RelationNotFoundException("There isn't any Issue related with this Event."))

        assertThrows<RelationNotFoundException> { this.service.findIssueByEventAction(EVENT_ACTION) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Issue related with this Event.")
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        `when`(this.issueRepository.findByNumber(anyInt()))
            .thenThrow(IssueNotFoundException("Issue #$ISSUE_NUMBER not found."))

        assertThrows<IssueNotFoundException> { this.service.findIssueByNumber(ISSUE_NUMBER) }.also {
            assertThat(it).isInstanceOf(IssueNotFoundException::class.java)
                .hasMessage("Issue #$ISSUE_NUMBER not found.")
        }
    }

}
