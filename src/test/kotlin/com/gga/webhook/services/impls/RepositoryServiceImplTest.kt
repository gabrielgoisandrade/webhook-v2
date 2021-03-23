package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.RepositoryModel
import com.gga.webhook.models.dTO.RepositoryDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.*

internal class RepositoryServiceImplTest : BaseServiceImplTestFactory() {

    private val service: RepositoryServiceImpl by lazy { RepositoryServiceImpl(this.repositoryRepository) }

    private val expectedModel: RepositoryModel = this.model.repository.apply { this.event = model.event }

    private val expectedDto: RepositoryDto = this.dto.repositoryDto()

    @Test
    fun saveRepository() {
        `when`(this.repositoryRepository.findByName(anyString())).thenReturn(Optional.empty())

        `when`(this.repositoryRepository.save(any(RepositoryModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveRepository(this.expectedModel)

        verify(this.repositoryRepository).save(any(RepositoryModel::class.java))
    }

    @Test
    fun getExistingRepository() {
        `when`(this.repositoryRepository.findByName(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveRepository(this.expectedModel)

        verify(this.repositoryRepository, never()).save(any(RepositoryModel::class.java))
    }

    @Test
    fun findRepositoryByName() {
        `when`(this.repositoryRepository.findByName(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.findRepositoryByName(REPOSITORY_NAME).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        `when`(this.repositoryRepository.findByName(anyString()))
            .thenThrow(RepositoryNotFoundException("Repository '$REPOSITORY_NAME' not found"))

        assertThrows<RepositoryNotFoundException> { this.service.findRepositoryByName(REPOSITORY_NAME) }.also {
            assertThat(it).isInstanceOf(RepositoryNotFoundException::class.java)
                .hasMessage("Repository '$REPOSITORY_NAME' not found")
        }
    }

}
