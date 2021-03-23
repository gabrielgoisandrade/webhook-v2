package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.services.impls.AssigneesServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.ASSIGNEES
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

@WebMvcTest(controllers = [AssigneesController::class])
internal class AssigneesControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: AssigneesServiceImpl

    @Autowired
    private lateinit var controller: AssigneesController

    private val expected: AssigneesDto = this.dto.assigneesDto()

    @Test
    fun findAssigneesByIssueNumber() {
        `when`(this.service.findAssigneesByIssueNumber(anyInt())).thenReturn(listOf(this.expected))

        this.controller.findAssigneesByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body!!.isNotEmpty()).isTrue
            assertThat(it.body).isEqualTo(listOf(this.expected))

            it.body!!.forEach { body: AssigneesDto ->
                with(body.links) {
                    assertThat(this.isEmpty).isFalse
                    assertThat(this.getLink("self").isPresent).isTrue
                }
            }
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        given(this.service.findAssigneesByIssueNumber(anyInt()))
            .willThrow(RelationNotFoundException("There isn't any Assignees related with this Issue"))

        this.mockMvc.perform(getRequest("$ASSIGNEES/issue/$ISSUE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Assignees related with this Issue"))
    }

}
