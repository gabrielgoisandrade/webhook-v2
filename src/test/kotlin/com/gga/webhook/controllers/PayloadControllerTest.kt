package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.PayloadNotFoundException
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.models.vO.PayloadVo
import com.gga.webhook.services.PayloadServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.PAYLOAD
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class PayloadControllerTest {

    @MockBean
    private lateinit var service: PayloadServiceImpl

    @Autowired
    private lateinit var controller: PayloadController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payload: PayloadDto = this.builder.payload()

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum objeto como null e retornar o status code 201.")
    fun saveFullPayload() {
        given(this.service.savePayload(this.payload)).willReturn(this.payload)

        this.controller.saveIssue(this.payload).also {
            assertEquals(it.statusCode, HttpStatus.CREATED)

            with(it.body!!) {
                assertAll(
                    {
                        assertFalse(this.repository!!.license == null, "License must not be null")
                        assertFalse(this.issue!!.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue!!.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue!!.assignees.isEmpty(), "Assignees must not be an empty list")
                        assertFalse(this.issue!!.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertEquals(payload, this, "The response body must be equal to mock")
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
                        assertFalse(this.repository!!.license == null, "License must not be null")
                        assertFalse(this.issue!!.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue!!.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue!!.assignees.isEmpty(), "Assignees must not be an empty list")
                    },
                    {
                        assertTrue(this.issue!!.labels.isEmpty(), "Labels must be an empty list")
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
                        assertFalse(this.repository!!.license == null, "License must not be null")
                        assertFalse(this.issue!!.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue!!.assignees.isEmpty(), "Assignees must be not an empty list")
                        assertFalse(this.issue!!.labels.isEmpty(), "Labels must be not an empty list")
                    },
                    {
                        assertTrue(this.issue!!.milestone == null, "Milestone must be null")
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
                        assertFalse(this.repository!!.license == null, "License must not be null")
                        assertFalse(this.issue!!.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue!!.labels.isEmpty(), "Labels must not be an empty list")
                    },
                    {
                        assertTrue(this.issue!!.assignees.isEmpty(), "Assignees must be an empty list")
                        assertTrue(this.issue!!.assignee == null, "Assignee must be null")
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
                        assertFalse(this.issue!!.milestone == null, "Milestone must not be null")
                        assertFalse(this.issue!!.assignee == null, "Assignee must not be null")
                        assertFalse(this.issue!!.assignees.isEmpty(), "Assignees must not an empty list")
                        assertFalse(this.issue!!.labels.isEmpty(), "Labels must not an empty list")
                    },
                    {
                        assertTrue(this.repository!!.license == null, "License must be null")
                        assertEquals(body, this, "The response body must be equal to mock")
                    }
                )
            }
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar o Payload com o id solicitado e HATEOAS configurado")
    fun getPayloadById() {
        val id: Long = this.payload.id

        val payloadVo: PayloadVo = this.payload convertTo PayloadVo::class.java

        given(this.service.getPayloadById(id)).willReturn(payloadVo)

        this.mockMvc.perform(getRequest("$PAYLOAD/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("action").value(payloadVo.action))
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não achar o Payload com o id solicitado")
    fun throwErrorToPayloadNotFound() {
        val id: Long = this.payload.id

        given(this.service.getPayloadById(id)).willThrow(PayloadNotFoundException("Payload with ID $id not found."))

        this.mockMvc.perform(getRequest("$PAYLOAD/$id"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Payload with ID $id not found."))
    }

    @Test
    @DisplayName("GET -> Deve retornar todos os Payloads com o HATEOAS configurado e paginação")
    fun getAllPayloads() {
        val payload: List<PayloadVo> = (listOf(this.payload) convertTo PayloadVo::class.java).toList()

        val page: PageImpl<PayloadVo> = PageImpl(payload)

        given(this.service.getAllPayloads(0, 10, "asc")).willReturn(page)

        this.mockMvc.perform(getRequest("$PAYLOAD/?limit=10&page=0"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("content").isNotEmpty)
    }

}