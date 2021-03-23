package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.OwnerModel
import com.gga.webhook.models.dTO.OwnerDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*


internal class OwnerServiceImplTest : BaseServiceImplTestFactory() {

    private val service: OwnerServiceImpl by lazy { OwnerServiceImpl(this.ownerRepository) }

    private val expectedModel: OwnerModel = this.model.owner

    private val expectedDto: OwnerDto = this.dto.ownerDto()

    @Test
    fun saveOwner() {
        `when`(this.ownerRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveOwner(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingOwner() {
        `when`(this.ownerRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        verify(this.ownerRepository, never()).save(any(OwnerModel::class.java))

        this.service.saveOwner(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun findOwnerByRepositoryName() {
        `when`(this.ownerRepository.findByRepositoryName(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.findOwnerByRepositoryName(REPOSITORY_NAME).also { assertThat(it).isEqualTo(this.expectedDto) }
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        `when`(this.ownerRepository.findByRepositoryName(anyString()))
            .thenThrow(RelationNotFoundException("There isn't any Owner related with this Repository"))

        assertThrows<RelationNotFoundException> { this.service.findOwnerByRepositoryName(REPOSITORY_NAME) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any Owner related with this Repository")
        }
    }

}
