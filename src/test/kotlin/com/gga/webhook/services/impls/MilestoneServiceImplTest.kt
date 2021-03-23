package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.MilestoneModel
import com.gga.webhook.models.dTO.MilestoneDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*

internal class MilestoneServiceImplTest : BaseServiceImplTestFactory() {

    private val service: MilestoneServiceImpl by lazy { MilestoneServiceImpl(this.milestoneRepository) }

    private val expectedModel: MilestoneModel = this.model.milestone

    private val expectedDto: MilestoneDto = this.dto.milestoneDto()

    @Test
    fun saveMilestone() {
        `when`(this.milestoneRepository.findByNumber(anyInt())).thenReturn(Optional.empty())

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveMilestone(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingMilestone() {
        `when`(this.milestoneRepository.findByNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))

        this.service.saveMilestone(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getNullMilestone() {
        this.service.saveMilestone(null).also { assertThat(it).isNull() }

        verify(this.milestoneRepository, never()).findByNumber(anyInt())

        verify(this.milestoneRepository, never()).save(any(MilestoneModel::class.java))
    }

    @Test
    fun findMilestoneByIssueNumber() {
        `when`(this.milestoneRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        this.service.findMilestoneByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        `when`(this.milestoneRepository.findByIssueNumber(anyInt()))
            .thenThrow(RelationNotFoundException("There isn't any Milestone related with this Issue"))

        assertThrows<RelationNotFoundException> { this.service.findMilestoneByIssueNumber(ISSUE_NUMBER) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Milestone related with this Issue")
        }
    }

}
