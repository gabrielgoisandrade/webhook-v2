package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.AssigneeDto
import com.gga.webhook.services.impls.AssigneeServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.ASSIGNEE
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

@WebMvcTest(controllers = [AssigneeController::class])
internal class AssigneeControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: AssigneeServiceImpl

    @Autowired
    private lateinit var controller: AssigneeController

    private val expected: AssigneeDto = this.dto.assigneeDto()

    @Test
    fun findAssigneeByIssueNumber() {
        `when`(this.service.findAssigneeByIssueNumber(anyInt())).thenReturn(this.expected)

        this.controller.findAssigneeByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        given(this.service.findAssigneeByIssueNumber(anyInt()))
            .willThrow(RelationNotFoundException("There isn't any Assignee related with this Issue"))

        this.mockMvc.perform(getRequest("$ASSIGNEE/issue/$ISSUE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Assignee related with this Issue"))
    }

}
