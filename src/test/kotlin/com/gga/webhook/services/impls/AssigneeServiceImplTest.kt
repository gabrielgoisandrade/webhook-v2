package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.AssigneeModel
import com.gga.webhook.models.dTO.AssigneeDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*


internal class AssigneeServiceImplTest : BaseServiceImplTestFactory() {

    private val service: AssigneeServiceImpl by lazy { AssigneeServiceImpl(this.assigneeRepository) }

    private val expectedModel: AssigneeModel = this.model.assignee

    private val expectedDto: AssigneeDto = this.dto.assigneeDto()

    @Test
    fun saveAssignee() {
        `when`(this.assigneeRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveAssignee(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingAssignee() {
        `when`(this.assigneeRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveAssignee(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }

        verify(this.assigneeRepository, never()).save(any(AssigneeModel::class.java))
    }

    @Test
    fun findAssigneeByIssueNumber() {
        `when`(this.assigneeRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        this.service.findAssigneeByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throeErrorByIssueNumberNotFound() {
        `when`(this.assigneeRepository.findByIssueNumber(anyInt()))
            .thenThrow(RelationNotFoundException("There isn't any Assignee related with this Issue"))

        assertThrows<RelationNotFoundException> { this.service.findAssigneeByIssueNumber(ISSUE_NUMBER) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Assignee related with this Issue")
        }
    }

}
