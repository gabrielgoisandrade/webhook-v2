package com.gga.webhook.services.impls

import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.IssueResponsibleModel
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

internal class IssueResponsibleServiceImplTest : BaseServiceImplTestFactory() {

    private val service: IssueResponsibleServiceImpl by lazy {
        IssueResponsibleServiceImpl(this.issueResponsibleRepository)
    }

    private val expectedIssue: IssueModel = this.model.issue

    private val expectedAssignees: AssigneesModel = this.model.assignees

    private val expectedIssueResponsible: IssueResponsibleModel =
        IssueResponsibleModel(issue = this.expectedIssue, assignees = this.expectedAssignees)

    @Test
    fun saveIssueResponsible() {
        `when`(this.issueResponsibleRepository.findByIssueIdAndAssigneesId(anyLong(), anyLong()))
            .thenReturn(Optional.empty())

        `when`(this.issueResponsibleRepository.save(any(IssueResponsibleModel::class.java)))
            .thenReturn((this.expectedIssueResponsible))

        this.service.saveIssueResponsible(this.expectedIssue, listOf(this.expectedAssignees))

        verify(this.issueResponsibleRepository).save(any(IssueResponsibleModel::class.java))
    }

    @Test
    fun adjustIssueResponsibleSets() {
        `when`(this.issueResponsibleRepository.findByIssueIdAndAssigneesId(anyLong(), anyLong()))
            .thenReturn(Optional.of(this.expectedIssueResponsible))

        `when`(this.issueResponsibleRepository.save(any(IssueResponsibleModel::class.java)))
            .thenReturn((this.expectedIssueResponsible))

        this.service.saveIssueResponsible(
            this.expectedIssue,
            listOf(this.model.assignees.apply { this.login = "newLogin" })
        )

        verify(this.issueResponsibleRepository).save(any(IssueResponsibleModel::class.java))
    }

    @Test
    fun doNotSave() {
        this.service.saveIssueResponsible(this.expectedIssue, emptyList())

        verify(this.issueResponsibleRepository, never())
            .findByIssueIdAndAssigneesId(anyLong(), anyLong())

        verify(this.issueResponsibleRepository, never())
            .save(any(IssueResponsibleModel::class.java))
    }

}