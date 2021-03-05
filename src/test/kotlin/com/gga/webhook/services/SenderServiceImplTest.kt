package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.repositories.SenderRepository
import com.gga.webhook.services.impls.SenderServiceImpl
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
internal class SenderServiceImplTest {

    @MockBean
    private lateinit var senderRepository: SenderRepository

    private val senderServiceImpl: SenderServiceImpl by lazy { SenderServiceImpl(this.senderRepository) }

    private val expectedSender: SenderVo =PayloadBuilder().senderDto() convertTo SenderVo::class.java

    @Test
    @DisplayName("Deve retornar o sender de determinado payload")
    fun getSender() {

        `when`(this.senderRepository.getSender()).thenReturn(this.expectedSender convertTo SenderModel::class.java)

        val sender: SenderVo = this.senderServiceImpl.getSender()

        assertEquals(expectedSender, sender)
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhum sender de determinado payload")
    fun throwErrorByNoSenderFound() {
        `when`(this.senderRepository.getSender()).thenThrow(SenderNotFoundException("No sender found."))

        assertThrows<SenderNotFoundException> { this.senderServiceImpl.getSender() }.also {
            assertThat(it).isInstanceOf(SenderNotFoundException::class.java).hasMessage("No sender found.")
        }
    }

}