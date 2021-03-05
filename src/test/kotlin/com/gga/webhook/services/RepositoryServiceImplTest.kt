package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.models.RepositoryModel
import com.gga.webhook.models.vO.RepositoryVo
import com.gga.webhook.repositories.RepositoryRepository
import com.gga.webhook.services.impls.RepositoryServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class RepositoryServiceImplTest {

    @MockBean
    private lateinit var repositoryRepository: RepositoryRepository

    private val repositoryServiceImpl: RepositoryServiceImpl by lazy { RepositoryServiceImpl(this.repositoryRepository) }

    private val expectedRepository: RepositoryVo = PayloadBuilder().repositoryDto() convertTo RepositoryVo::class.java

    @Test
    @DisplayName("Deve retornar o repository de determinado payload")
    fun getRepository() {

        `when`(this.repositoryRepository.getRepository()).thenReturn(this.expectedRepository convertTo RepositoryModel::class.java)

        val repository: RepositoryVo = this.repositoryServiceImpl.getRepository()

        assertEquals(expectedRepository, repository)
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhum repository de determinado payload")
    fun throwErrorByNoRepositoryFound() {
        `when`(this.repositoryRepository.getRepository()).thenThrow(RepositoryNotFoundException("No repository found."))

        assertThrows<RepositoryNotFoundException> { this.repositoryServiceImpl.getRepository() }.also {
            assertThat(it).isInstanceOf(RepositoryNotFoundException::class.java).hasMessage("No repository found.")
        }
    }

}