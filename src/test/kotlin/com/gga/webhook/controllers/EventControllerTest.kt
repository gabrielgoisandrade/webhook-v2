package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.IssueNotFound
import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.services.EventService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class EventControllerTest {

    @MockBean
    private lateinit var service: EventService

    @Autowired
    private lateinit var controller: EventController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val builder: PayloadBuilder = PayloadBuilder()

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum objeto como null e retornar o status code 201.")
    fun saveFullPayload() {
        val body: PayloadDto = this.builder.payload()

        given(this.service.savePayload(body)).willReturn(body)

        this.controller.saveIssue(body).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.repository.license == null, "License must not be null")
                        assertFalse(this.issue.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.issue.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhuma label e retornar o status code 201.")
    fun savePayloadWithEmptyLabelAtIssue() {
        val body: PayloadDto = this.builder.issueWithoutLabels()

        given(this.service.savePayload(body)).willReturn(body)

        this.controller.saveIssue(body).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.repository.license == null, "License must not be null")
                        assertFalse(this.issue.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue.assignees.isEmpty(), "Assignees must not be an empty list")
                    },
                    {
                        assertTrue(this.issue.labels.isEmpty(), "Labels must be an empty list")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum milestone e retornar o status code 201.")
    fun savePayloadWitNullMilestoneAtIssue() {
        val body: PayloadDto = this.builder.issueWithoutMilestone()

        given(this.service.savePayload(body)).willReturn(body)

        this.controller.saveIssue(body).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.repository.license == null, "License must not be null")
                        assertFalse(this.issue.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue.assignees.isEmpty(), "Assignees must be not an empty list")
                        assertFalse(this.issue.labels.isEmpty(), "Labels must be not an empty list")
                    },
                    {
                        assertTrue(this.issue.milestone == null, "Milestone must be null")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum assignee/assignees e retornar o status code 201.")
    fun savePayloadWithoutAssigneeAtIssue() {
        val body: PayloadDto = this.builder.issueWithoutAssignee()

        given(this.service.savePayload(body)).willReturn(body)

        this.controller.saveIssue(body).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.repository.license == null, "License must not be null")
                        assertFalse(this.issue.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertTrue(this.issue.assignees.isEmpty(), "Assignees must be an empty list")
                        assertTrue(this.issue.assignee == null, "Assignee must be null")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhuma licença e retornar o status code 201.")
    fun savePayloadWithoutLicenseAtRepository() {
        val body: PayloadDto = this.builder.repositoryWithoutLicense()

        given(this.service.savePayload(body)).willReturn(body)

        this.controller.saveIssue(body).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.issue.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue.assignees.isEmpty(), "Assignees must not an empty list")
                        assertFalse(this.issue.labels.isEmpty(), "Labels must not an empty list")
                    },
                    {
                        assertTrue(this.repository.license == null, "License must be null")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum objeto interno como null e o " +
                "status code 200"
    )
    fun getIssueByNumberWithoutNullObjects() {
        val body: IssueDto = this.builder.payload().issue

        given(this.service.getIssueByNumber(body.number)).willReturn(body)

        this.controller.getIssueByNumber(body.number).also {
            assertEquals(it.statusCode, HttpStatus.OK)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.assignee == null, "Assignee must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, com uma lista vazia de labels " +
                "e o status code 200"
    )
    fun getIssueByNumberWithEmptyLabels() {
        val body: IssueDto = this.builder.issueWithoutLabels().issue

        given(this.service.getIssueByNumber(body.number)).willReturn(body)

        this.controller.getIssueByNumber(body.number).also {
            assertEquals(it.statusCode, HttpStatus.OK)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.assignee == null, "Assignee must not be null")
                    },
                    {
                        assertTrue(this.labels.isEmpty(), "Labels must be an empty list")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum milestone e o status code 200"
    )
    fun getIssueByNumberWithNullMilestone() {
        val body: IssueDto = this.builder.issueWithoutMilestone().issue

        given(this.service.getIssueByNumber(body.number)).willReturn(body)

        this.controller.getIssueByNumber(body.number).also {
            assertEquals(it.statusCode, HttpStatus.OK)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.assignee == null, "Assignee must not be null")
                        assertFalse(this.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertTrue(this.milestone == null, "Milestone must be null")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName(
        "GET -> Deve retornar a issue com o número passado via path, sem nenhum assignee, uma lista vazia de " +
                "assignees e o status code 200"
    )
    fun getIssueByNumberWithNullAssignee() {
        val body: IssueDto = this.builder.issueWithoutAssignee().issue

        given(this.service.getIssueByNumber(body.number)).willReturn(body)

        this.controller.getIssueByNumber(body.number).also {
            assertEquals(it.statusCode, HttpStatus.OK)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.milestone == null, "Milestone must not be null")
                        assertFalse(this.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertTrue(this.assignee == null, "Assignee must be null")
                        assertTrue(this.assignees.isEmpty(), "Assignees must be an empty list")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar uma mensagem de erro ao buscar uma issue que não existe")
    fun throwErrorIssueNotFound() {
        val body: IssueDto = this.builder.payload().issue

        given(this.service.getIssueByNumber(body.number)).willThrow(IssueNotFound("Issue #${body.number} not found."))

        val request: MockHttpServletRequestBuilder = get("/issue/${body.number}")
            .accept(APPLICATION_JSON)

        this.mockMvc.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("message").value("Issue #${body.number} not found."))
    }

}