package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.RepositoryDto
import com.gga.webhook.services.impls.RepositoryServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.REPOSITORY
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

@WebMvcTest(controllers = [RepositoryController::class])
internal class RepositoryControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: RepositoryServiceImpl

    @Autowired
    private lateinit var controller: RepositoryController

    private val expected: RepositoryDto = this.dto.repositoryDto()

    @Test
    fun findRepositoryByName() {
        `when`(this.service.findRepositoryByName(anyString())).thenReturn(this.expected)

        this.controller.findRepositoryByName(this.expected.name).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("owner").isPresent).isTrue
                assertThat(this.getLink("license").isPresent).isTrue
            }
        }
    }

    @Test
    fun findRepositoryByNameWithoutLicense() {
        `when`(this.service.findRepositoryByName(anyString())).thenReturn(this.expected.apply { this.license = null })

        this.controller.findRepositoryByName(REPOSITORY_NAME).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("owner").isPresent).isTrue
                assertThat(this.getLink("license").isPresent).isFalse
            }
        }
    }

    @Test
    fun findRepositoryByEventAction() {
        `when`(this.service.findRepositoryByEventAction(anyString())).thenReturn(this.expected)

        this.controller.findRepositoryByEventAction(EVENT_ACTION).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("owner").isPresent).isTrue
                assertThat(this.getLink("license").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByEventActionNotFound() {
        given(this.service.findRepositoryByEventAction(anyString()))
            .willThrow(RelationNotFoundException("There isn't any Repository related with this Event."))

        this.mockMvc.perform(getRequest("$REPOSITORY/event/$EVENT_ACTION"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Repository related with this Event."))
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        given(this.service.findRepositoryByName(anyString()))
            .willThrow(RepositoryNotFoundException("Repository '$REPOSITORY_NAME' not found"))

        this.mockMvc.perform(getRequest("$REPOSITORY/$REPOSITORY_NAME"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Repository '$REPOSITORY_NAME' not found"))
    }

}
