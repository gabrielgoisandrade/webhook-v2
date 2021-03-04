package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.services.SenderServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.SENDER
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class SenderControllerTest {

    @MockBean
    private lateinit var senderServiceImpl: SenderServiceImpl

    @Autowired
    private lateinit var senderController: SenderController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedSender: SenderVo = PayloadBuilder().senderDto() convertTo SenderVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o sender com as mesmas propriedades que o esperado")
    fun verifySenderBody() {
        `when`(this.senderServiceImpl.getSender()).thenReturn((this.expectedSender))

        this.senderController.getSender().also {
            assertEquals(expectedSender, it.body!!, "The body must be equal to expected")
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar o sender com o HATEOAS configurado")
    fun verifySenderLink() {
        given(this.senderServiceImpl.getSender()).willReturn((this.expectedSender))

        this.mockMvc.perform(getRequest(SENDER))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

}