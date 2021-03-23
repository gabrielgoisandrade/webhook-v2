package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.OwnerDto
import com.gga.webhook.services.impls.OwnerServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.OWNER
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

@WebMvcTest(controllers = [OwnerController::class])
internal class OwnerControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: OwnerServiceImpl

    @Autowired
    private lateinit var controller: OwnerController

    private val expected: OwnerDto = this.dto.ownerDto()

    @Test
    fun findOwnerByRepositoryName() {
        `when`(this.service.findOwnerByRepositoryName(anyString())).thenReturn(this.expected)

        this.controller.findOwnerByRepositoryName(REPOSITORY_NAME).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        given(this.service.findOwnerByRepositoryName(anyString()))
            .willThrow(RelationNotFoundException("There isn't any Owner related with this Repository"))

        this.mockMvc.perform(getRequest("$OWNER/repository/$REPOSITORY_NAME"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Owner related with this Repository"))
    }

}