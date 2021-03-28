package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.models.dTO.LabelsDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.util.*

internal class LabelsServiceImplTest : BaseServiceImplTestFactory() {

    private val service: LabelsServiceImpl by lazy { LabelsServiceImpl(this.labelsRepository) }

    private val expectedModel: LabelsModel = this.model.labels

    private val expectedDto: LabelsDto = this.dto.labelsDto()

    @Test
    fun saveLabels() {
        `when`(this.labelsRepository.findByName(anyString())).thenReturn(Optional.empty())

        `when`(this.labelsRepository.saveAll(anyList())).thenReturn(listOf(this.expectedModel))

        this.service.saveLabels(listOf(this.expectedModel)).also {
            assertThat(it).isEqualTo(listOf(this.expectedModel))
        }
    }

    @Test
    fun getExistingLabels() {
        `when`(this.labelsRepository.findByName(anyString()))
            .thenReturn(Optional.of(this.expectedModel))

        this.service.saveLabels(listOf(this.expectedModel))
            .also { assertThat(it).isEqualTo(listOf(this.expectedModel)) }

        verify(this.labelsRepository, never()).saveAll(anyList())
    }

    @Test
    fun findLabelsByIssueNumber() {
        `when`(this.labelsRepository.findByIssueNumber(ISSUE_NUMBER))
            .thenReturn(Optional.of(listOf(this.expectedModel)))

        val expectedPage: Page<LabelsDto> = PageImpl(listOf(this.expectedDto))

        this.service.findLabelsByIssueNumber(ISSUE_NUMBER, 0, 5, "asc").also {
            assertThat(it.content).isNotEmpty
            assertThat(it.content).isEqualTo(expectedPage.content)
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        `when`(this.labelsRepository.findByIssueNumber(ISSUE_NUMBER))
            .thenThrow(RelationNotFoundException("There isn't any Labels related with this Issue"))

        assertThrows<RelationNotFoundException> {
            this.service.findLabelsByIssueNumber(ISSUE_NUMBER, 0, 5, "asc")
        }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Labels related with this Issue")
        }
    }

}