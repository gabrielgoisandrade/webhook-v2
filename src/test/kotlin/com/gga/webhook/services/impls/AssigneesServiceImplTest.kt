package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.dTO.AssigneesDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

internal class AssigneesServiceImplTest : BaseServiceImplTestFactory() {

    private val service: AssigneesServiceImpl by lazy { AssigneesServiceImpl(this.assigneesRepository) }

    private val expectedModel: AssigneesModel = this.model.assignees

    private val expectedDto: AssigneesDto = this.dto.assigneesDto()

    @Test
    fun saveAssignees() {
        `when`(this.assigneesRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.assigneesRepository.saveAll(anyList())).thenReturn(listOf(this.expectedModel))

        this.service.saveAssignees(listOf(this.expectedModel))
            .also { assertThat(it).isEqualTo(listOf(this.expectedModel)) }
    }

    @Test
    fun getExistingAssignees() {
        `when`(this.assigneesRepository.findByLogin(anyString()))
            .thenReturn(Optional.of(this.expectedModel))

        this.service.saveAssignees(listOf(this.expectedModel)).also {
            assertThat(it).isEqualTo(listOf(this.expectedModel))
        }

        verify(this.assigneesRepository, never()).saveAll(anyList())
    }

    @Test
    fun getNullAssignees() {
        this.service.saveAssignees(emptyList()).also { assertThat(it).isEmpty() }

        verify(this.assigneesRepository, never()).findByLogin(anyString())

        verify(this.assigneesRepository, never()).saveAll(anyList())
    }

    @Test
    fun findAssigneesByIssueNumber() {
        `when`(this.assigneesRepository.findByIssueNumber(ISSUE_NUMBER))
            .thenReturn(Optional.of(listOf(this.expectedModel)))

        this.service.findAssigneesByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it.isEmpty()).isFalse
            assertThat(it).isEqualTo(listOf(this.expectedDto))
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        `when`(this.assigneesRepository.findByIssueNumber(ISSUE_NUMBER))
            .thenThrow(RelationNotFoundException("There isn't any Assignees related with this Issue"))

        assertThrows<RelationNotFoundException> { this.service.findAssigneesByIssueNumber(ISSUE_NUMBER) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Assignees related with this Issue")
        }
    }

}