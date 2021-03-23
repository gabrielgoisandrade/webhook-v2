package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.MILESTONE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.dTO.CreatorDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*


internal class CreatorServiceImplTest : BaseServiceImplTestFactory() {

    private val service: CreatorServiceImpl by lazy { CreatorServiceImpl(this.creatorRepository) }

    private val expectedModel: CreatorModel = this.model.creator

    private val expectedDto: CreatorDto = this.dto.creatorDto()

    @Test
    fun saveCreator() {
        `when`(this.creatorRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveCreator(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingCreator() {
        `when`(this.creatorRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveCreator(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }

        verify(this.creatorRepository, never()).save(any(CreatorModel::class.java))
    }

    @Test
    fun findCreatorByMilestoneNumber() {
        `when`(this.creatorRepository.findByMilestoneNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        this.service.findCreatorByMilestoneNumber(MILESTONE_NUMBER).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throwErrorByMilestoneNumberNotFound() {
        `when`(this.creatorRepository.findByMilestoneNumber(anyInt()))
            .thenThrow(RelationNotFoundException("There isn't any Creator related with this Milestone."))

        assertThrows<RelationNotFoundException> {
            this.service.findCreatorByMilestoneNumber(MILESTONE_NUMBER)
        }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Creator related with this Milestone.")
        }
    }

}
