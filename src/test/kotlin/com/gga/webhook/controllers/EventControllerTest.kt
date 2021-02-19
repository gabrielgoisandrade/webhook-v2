package com.gga.webhook.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.dto.AssigneesDto
import com.gga.webhook.models.dto.LabelDto
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class EventControllerTest {

    companion object {
        private const val ISSUE: String = "/issue"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val builder: PayloadBuilder = PayloadBuilder()

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum objeto como null e retornar o status code 201.")
    fun saveFullPayload() {
        val body: String = this.jsonParser(this.builder.payloadBuilder())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", hasSize<Int>(1)))
            .andExpect(jsonPath("issue['milestone']", notNullValue()))
            .andExpect(jsonPath("issue['milestone']['creator']", notNullValue()))
            .andExpect(jsonPath("issue['assignee']", notNullValue()))
            .andExpect(jsonPath("issue['assignees']", hasSize<Int>(1)))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", notNullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhuma label e retornar o status code 201.")
    fun savePayloadWithEmptyLabelAtIssue() {
        val body: String = this.jsonParser(this.builder.issueWithoutLabels())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", empty<HashSet<LabelDto>>()))
            .andExpect(jsonPath("issue['milestone']", notNullValue()))
            .andExpect(jsonPath("issue['milestone']['creator']", notNullValue()))
            .andExpect(jsonPath("issue['assignee']", notNullValue()))
            .andExpect(jsonPath("issue['assignees']", hasSize<Int>(1)))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", notNullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum milestone e retornar o status code 201.")
    fun savePayloadWitNullMilestoneAtIssue() {
        val body: String = this.jsonParser(this.builder.issueWithoutMilestone())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", hasSize<Int>(1)))
            .andExpect(jsonPath("issue['milestone']", nullValue()))
            .andExpect(jsonPath("issue['assignee']", notNullValue()))
            .andExpect(jsonPath("issue['assignees']", hasSize<Int>(1)))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", notNullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum assignee e retornar o status code 201.")
    fun savePayloadWithoutAssigneeAtIssue() {
        val body: String = this.jsonParser(this.builder.issueWithoutAssignee())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", hasSize<Int>(1)))
            .andExpect(jsonPath("issue['milestone']", notNullValue()))
            .andExpect(jsonPath("issue['milestone']['creator']", notNullValue()))
            .andExpect(jsonPath("issue['assignee']", nullValue()))
            .andExpect(jsonPath("issue['assignees']", hasSize<Int>(1)))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", notNullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhum assignees e retornar o status code 201.")
    fun savePayloadWithoutAssigneesAtIssue() {
        val body: String = this.jsonParser(this.builder.issueWithoutAssignees())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", hasSize<Int>(1)))
            .andExpect(jsonPath("issue['milestone']", notNullValue()))
            .andExpect(jsonPath("issue['milestone']['creator']", notNullValue()))
            .andExpect(jsonPath("issue['assignee']", notNullValue()))
            .andExpect(jsonPath("issue['assignees']", empty<HashSet<AssigneesDto>>()))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", notNullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    @Test
    @DisplayName("POST -> Deve salvar o Payload, sem nenhuma licença e retornar o status code 201.")
    fun savePayloadWithoutLicenseAtRepository() {
        val body: String = this.jsonParser(this.builder.repositoryWithoutLicense())

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
            .andExpect(jsonPath("issue", notNullValue()))
            .andExpect(jsonPath("issue['labels']", hasSize<Int>(1)))
            .andExpect(jsonPath("issue['milestone']", notNullValue()))
            .andExpect(jsonPath("issue['milestone']['creator']", notNullValue()))
            .andExpect(jsonPath("issue['assignee']", notNullValue()))
            .andExpect(jsonPath("issue['assignees']", hasSize<Int>(1)))
            .andExpect(jsonPath("repository", notNullValue()))
            .andExpect(jsonPath("repository['license']", nullValue()))
            .andExpect(jsonPath("repository['owner']", notNullValue()))
            .andExpect(jsonPath("sender", notNullValue()))
    }

    private fun <T> jsonParser(value: T): String = ObjectMapper()
        .registerModules(KotlinModule(nullIsSameAsDefault = true), JavaTimeModule())
        .writeValueAsString(value)

    private fun httpGet(): MockHttpServletRequestBuilder = get(ISSUE)
        .accept(MediaType.APPLICATION_JSON)

    private fun httpPost(json: String): MockHttpServletRequestBuilder = post(ISSUE)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json)

}