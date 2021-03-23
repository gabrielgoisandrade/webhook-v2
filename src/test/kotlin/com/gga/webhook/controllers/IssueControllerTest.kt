package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.services.impls.IssueServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.ISSUE
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

@WebMvcTest(controllers = [IssueController::class])
internal class IssueControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: IssueServiceImpl

    @Autowired
    private lateinit var controller: IssueController

    private val expected: IssueDto = this.dto.issueDto()

    @Test
    fun findIssueByNumber() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected)

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("assignee").isPresent).isTrue
                assertThat(this.getLink("milestone").isPresent).isTrue
            }
        }
    }

    @Test
    fun findIssueByNumberWithoutAssignee() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected.apply { this.assignee = null })

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("labels").isPresent).isTrue
                assertThat(this.getLink("assignee").isPresent).isFalse
                assertThat(this.getLink("assignees").isPresent).isTrue
                assertThat(this.getLink("milestone").isPresent).isTrue
            }
        }
    }

    @Test
    fun findIssueByNumberWithoutMilestone() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected.apply { this.milestone = null })

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("labels").isPresent).isTrue
                assertThat(this.getLink("assignee").isPresent).isTrue
                assertThat(this.getLink("assignees").isPresent).isTrue
                assertThat(this.getLink("milestone").isPresent).isFalse
            }
        }
    }

    @Test
    fun findIssueByNumberWithoutAssignees() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected.apply {
            this.assignees = emptyList()
        })

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("labels").isPresent).isTrue
                assertThat(this.getLink("assignee").isPresent).isTrue
                assertThat(this.getLink("assignees").isPresent).isFalse
                assertThat(this.getLink("milestone").isPresent).isTrue
            }
        }
    }

    @Test
    fun findIssueByNumberWithoutLabels() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected.apply { this.labels = emptyList() })

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("labels").isPresent).isFalse
                assertThat(this.getLink("assignee").isPresent).isTrue
                assertThat(this.getLink("assignees").isPresent).isTrue
                assertThat(this.getLink("milestone").isPresent).isTrue
            }
        }
    }

    @Test
    fun findIssueByNumberWithOnlyUser() {
        `when`(this.service.findIssueByNumber(anyInt())).thenReturn(this.expected.apply {
            this.assignee = null
            this.assignees = emptyList()
            this.labels = emptyList()
            this.milestone = null
        })

        this.controller.findIssueByNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
                assertThat(this.getLink("user").isPresent).isTrue
                assertThat(this.getLink("labels").isPresent).isFalse
                assertThat(this.getLink("assignee").isPresent).isFalse
                assertThat(this.getLink("assignees").isPresent).isFalse
                assertThat(this.getLink("milestone").isPresent).isFalse
            }
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        given(this.service.findIssueByNumber(anyInt()))
            .willThrow(IssueNotFoundException("Issue #$ISSUE_NUMBER not found."))

        this.mockMvc.perform(getRequest("$ISSUE/$ISSUE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Issue #$ISSUE_NUMBER not found."))
    }

}
