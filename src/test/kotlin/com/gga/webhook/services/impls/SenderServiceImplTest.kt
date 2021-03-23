package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.LOGIN
import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dTO.SenderDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.*

internal class SenderServiceImplTest : BaseServiceImplTestFactory() {

    private val service: SenderServiceImpl by lazy { SenderServiceImpl(this.senderRepository) }

    private val expectedModel: SenderModel = this.model.sender.apply { this.event = model.event }

    private val expectedDto: SenderDto = this.dto.senderDto()

    @Test
    fun saveSender() {
        `when`(this.senderRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.senderRepository.save(any(SenderModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveSender(this.expectedModel)

        verify(this.senderRepository).save(any(SenderModel::class.java))
    }

    @Test
    fun getExistingSender() {
        `when`(this.senderRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveSender(this.expectedModel)

        verify(this.senderRepository, never()).save(any(SenderModel::class.java))
    }

    @Test
    fun findSenderByLogin() {
        `when`(this.senderRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.findSenderByLogin(LOGIN).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throwErrorBySenderLoginNotFound() {
        `when`(this.senderRepository.findByLogin(anyString()))
            .thenThrow(SenderNotFoundException("Sender '$LOGIN' not found"))

        assertThrows<SenderNotFoundException> { this.service.findSenderByLogin(LOGIN) }.also {
            assertThat(it).isInstanceOf(SenderNotFoundException::class.java)
                .hasMessage("Sender '$LOGIN' not found")
        }
    }

}
