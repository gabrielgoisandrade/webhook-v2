package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dTO.SenderDto
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.repositories.SenderRepository
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

    private val senderDto: SenderDto = PayloadBuilder().senderDto()

    @Test
    @DisplayName("Deve retornar os senders de determinado payload")
    fun getSenders() {
        val expectedSenders: SenderVo = this.senderDto convertTo SenderVo::class.java

        `when`(this.senderRepository.getSender()).thenReturn(expectedSenders convertTo SenderModel::class.java)

        val senders: SenderVo = this.senderServiceImpl.getSender()

        assertEquals(expectedSenders, senders)
    }
}