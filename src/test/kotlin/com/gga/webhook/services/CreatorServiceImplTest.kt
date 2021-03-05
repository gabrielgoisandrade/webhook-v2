package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.CreatorNotFoundException
import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.vO.CreatorVo
import com.gga.webhook.repositories.CreatorRepository
import com.gga.webhook.services.impls.CreatorServiceImpl
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
internal class CreatorServiceImplTest {

    @MockBean
    private lateinit var creatorRepository: CreatorRepository

    private val creatorServiceImpl: CreatorServiceImpl by lazy { CreatorServiceImpl(this.creatorRepository) }

    private val expectedCreator: CreatorVo = PayloadBuilder().creatorDto() convertTo CreatorVo::class.java

    @Test
    @DisplayName("Deve retornar o creator de determinada issue")
    fun getCreator() {

        `when`(this.creatorRepository.getCreator()).thenReturn(this.expectedCreator convertTo CreatorModel::class.java)

        val creator: CreatorVo = this.creatorServiceImpl.getCreator()

        assertEquals(expectedCreator, creator)
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhum creator de determinado payload")
    fun throwErrorByNoCreatorFound() {
        `when`(this.creatorRepository.getCreator()).thenThrow(CreatorNotFoundException("No creator found."))

        assertThrows<CreatorNotFoundException> { this.creatorServiceImpl.getCreator() }.also {
            assertThat(it).isInstanceOf(CreatorNotFoundException::class.java).hasMessage("No creator found.")
        }
    }

}