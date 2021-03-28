package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.constants.MockValuesConstant.LOGIN
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.SenderDto
import com.gga.webhook.services.impls.SenderServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.SENDER
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.`when`
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SenderController::class])
internal class SenderControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: SenderServiceImpl

    @Autowired
    private lateinit var controller: SenderController

    private val expected: SenderDto = this.dto.senderDto()

    @Test
    fun findSenderByLogin() {
        `when`(this.service.findSenderByLogin(anyString())).thenReturn(this.expected)

        this.controller.findSenderByLogin(LOGIN).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun findSenderByEventAction() {
        `when`(this.service.findSenderByEventAction(anyString())).thenReturn(this.expected)

        this.controller.findSenderByEventAction(EVENT_ACTION).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByEventActionNotFound() {
        given(this.service.findSenderByEventAction(anyString()))
            .willThrow(RelationNotFoundException("There isn't any Sender related with this Event"))

        this.mockMvc.perform(getRequest("$SENDER/event/$EVENT_ACTION"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Sender related with this Event"))
    }

    @Test
    fun throwErrorBySenderLoginNotFound() {
        given(this.service.findSenderByLogin(anyString()))
            .willThrow(SenderNotFoundException("Sender '$LOGIN' not found"))

        this.mockMvc.perform(getRequest("$SENDER/$LOGIN"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Sender '$LOGIN' not found"))
    }

}