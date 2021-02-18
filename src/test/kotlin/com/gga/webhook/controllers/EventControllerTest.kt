package com.gga.webhook.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.gga.webhook.models.dto.*
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class EventControllerTest {

    companion object {

        private const val ISSUE: String = "/issue"

    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("POST -> Deve salvar a issue e retornar um status code 201")
    fun saveIssue() {
        val body: String =
            this.jsonParser(
                PayloadDto(
                    "closed",
                    IssueDto(user = UserDto()),
                    RepositoryDto(owner = OwnerDto()),
                    SenderDto()
                )
            )

        val request: MockHttpServletRequestBuilder = httpPost(body)

        this.mockMvc.perform(request).andExpect(status().isCreated)
    }

    private fun <T> jsonParser(value: T): String =
        ObjectMapper().writeValueAsString(value)

    private fun httpGet(): MockHttpServletRequestBuilder = get(ISSUE)
        .accept(MediaType.APPLICATION_JSON)

    private fun httpPost(json: String): MockHttpServletRequestBuilder = post(ISSUE)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json)

}