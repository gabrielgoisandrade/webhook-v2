package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.services.IssueServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.ISSUE
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class IssueControllerTest {

    @MockBean
    private lateinit var serviceImpl: IssueServiceImpl

    @Autowired
    private lateinit var controller: IssueController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val builder: PayloadBuilder = PayloadBuilder()

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum assignee, uma lista vazia de " +
                "assignees e o status code 200"
    )
    fun getIssueByNumberWithNullAssignee() {
        val body: HashSet<IssueDto> = hashSetOf(this.builder.issueWithoutAssignee().issue!!)

        given(this.serviceImpl.getIssueByNumber(50)).willReturn(body)

        this.controller.getIssueByNumber(50).also {
            assertEquals(it.statusCode, HttpStatus.OK)

            assertEquals(body, it.body!!, "The response body must be equal to mock")

            it.body!!.forEach { issueDto: IssueDto ->
                with(issueDto) {
                    assertAll({
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                        assertTrue(this.assignee == null, "Assignee must be null")
                        assertTrue(this.assignees.isEmpty(), "Assignees must be an empty list")
                    })
                }
            }
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar uma mensagem de erro ao buscar uma issue que não existe")
    fun throwErrorIssueNotFound() {
        val body: IssueDto = this.builder.payload().issue!!

        given(this.serviceImpl.getIssueByNumber(body.number))
            .willThrow(IssueNotFoundException("Issue #${body.number} not found."))

        this.mockMvc.perform(getRequest("$ISSUE/${body.number}"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Issue #${body.number} not found."))
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum objeto interno como null e o " +
                "status code 200"
    )
    fun getIssueByNumberWithoutNullObjects() {
        val body: HashSet<IssueDto> = hashSetOf(this.builder.payload().issue!!)

        given(this.serviceImpl.getIssueByNumber(5)).willReturn(body)

        this.controller.getIssueByNumber(5).also {
            assertEquals(HttpStatus.OK, it.statusCode)

            assertEquals(body, it.body!!, "The response body must be equal to mock")

            it.body!!.forEach { issueDto: IssueDto ->
                with(issueDto) {
                    assertAll({
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.assignee == null, "Assignee must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                    })
                }
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, com uma lista vazia de labels " +
                "e o status code 200"
    )
    fun getIssueByNumberWithEmptyLabels() {
        val body: HashSet<IssueDto> = hashSetOf(this.builder.issueWithoutLabels().issue!!)

        given(this.serviceImpl.getIssueByNumber(10)).willReturn(body)

        this.controller.getIssueByNumber(10).also {
            assertEquals(HttpStatus.OK, it.statusCode)

            assertEquals(body, it.body!!, "The response body must be equal to mock")

            it.body!!.forEach { issueDto: IssueDto ->
                with(issueDto) {
                    assertAll({
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.assignee == null, "Assignee must not be null")
                        assertTrue(this.labels.isEmpty(), "Labels must be an empty list")
                    })
                }
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum milestone e o status code 200"
    )
    fun getIssueByNumberWithNullMilestone() {
        val body: HashSet<IssueDto> = hashSetOf(this.builder.issueWithoutMilestone().issue!!)

        given(this.serviceImpl.getIssueByNumber(11)).willReturn(body)

        this.controller.getIssueByNumber(11).also {
            assertEquals(HttpStatus.OK, it.statusCode)

            assertEquals(body, it.body!!, "The response body must be equal to mock")

            it.body!!.forEach { issueDto: IssueDto ->
                with(issueDto) {
                    assertAll({
                        assertFalse(this.assignee == null, "Assignee must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                        assertTrue(this.milestone == null, "Milestone must be null")
                    })
                }
            }
        }
    }

}