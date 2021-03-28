package com.gga.webhook.controllers

import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.services.impls.PayloadServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus

@SpringBootTest
internal class PayloadControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: PayloadServiceImpl

    @Autowired
    private lateinit var controller: PayloadController

    private val payload: PayloadDto = this.dto.payloadDto()

    @Test
    @DisplayName("Must save PayloadDto and return EventDto with status code 201")
    fun payload() {
        val expectedResponse: EventDto = this.dto.eventDto()

        `when`(this.service.savePayloadData(this.payload)).thenReturn(expectedResponse)

        this.controller.savePayload(this.payload).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.CREATED)

            with(it.body!!) {
                assertThat(this).isNotNull
                assertThat(this).isEqualTo(expectedResponse)
                assertThat(this.links.isEmpty).isFalse
                assertThat(this.links.getLink("self").isPresent).isTrue
                assertThat(this.links.getLink("event").isPresent).isTrue
                assertThat(this.links.getLink("issue").isPresent).isTrue
                assertThat(this.links.getLink("repository").isPresent).isTrue
                assertThat(this.links.getLink("sender").isPresent).isTrue
            }
        }
    }

}