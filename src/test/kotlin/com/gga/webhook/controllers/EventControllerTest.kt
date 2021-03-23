package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.errors.exceptions.EventNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.services.impls.EventServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.EVENT
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [EventController::class])
internal class EventControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: EventServiceImpl

    @Autowired
    private lateinit var controller: EventController

    private val expected: EventDto = this.dto.eventDto()

    @Test
    fun findEventByAction() {
        `when`(this.service.findEventByAction(anyString())).thenReturn(this.expected)

        this.controller.findEventByAction(EVENT_ACTION).also {
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
        given(this.service.findEventByAction(anyString()))
            .willThrow(EventNotFoundException("Event with action '$EVENT_ACTION' not found"))

        this.mockMvc.perform(getRequest("$EVENT/$EVENT_ACTION"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Event with action '$EVENT_ACTION' not found"))

    }

}