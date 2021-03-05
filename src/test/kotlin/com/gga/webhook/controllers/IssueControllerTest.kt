package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.models.vO.IssueVo
import com.gga.webhook.services.impls.IssueServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.ISSUE
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
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

    private val expectedIssue: IssueVo = this.builder.issue() convertTo IssueVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar uma mensagem de erro ao buscar uma issue que não existe")
    fun throwErrorIssueNotFound() {
        val body: IssueVo = this.builder.payload().issue!! convertTo IssueVo::class.java

        given(this.serviceImpl.getIssueByNumber(body.number))
            .willThrow(IssueNotFoundException("Issue #${body.number} not found."))

        this.mockMvc.perform(getRequest("$ISSUE/${body.number}"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("Issue #${body.number} not found."))
    }

    @Test
    @DisplayName("GET -> Deve retornar a issue com o número passado via path")
    fun getIssueByNumber() {
        val body: HashSet<IssueVo> = hashSetOf(this.builder.payload().issue!! convertTo IssueVo::class.java)

        given(this.serviceImpl.getIssueByNumber(5)).willReturn(body)

        this.controller.getIssueByNumber(5).also {
            assertAll({
                assertEquals(HttpStatus.OK, it.statusCode, "The status code must be equal to 200")
                assertEquals(body, it.body!!, "The response body must be equal to mock")
            })
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar a issue com as mesmas propriedades que o esperado")
    fun verifyIssueBody() {
        `when`(this.serviceImpl.getIssue()).thenReturn((this.expectedIssue))

        this.controller.getIssue().also {
            assertAll({
                assertEquals(HttpStatus.OK, it.statusCode, "The status code must be equal to 200")
                assertEquals(this.expectedIssue, it.body!!, "The response body must be equal to expected")
            })
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar a issue correspondente ao payload atual com o HATEOAS configurado")
    fun verifyIssueLink() {
        given(this.serviceImpl.getIssue()).willReturn((this.expectedIssue))

        this.mockMvc.perform(getRequest(ISSUE))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
            .andExpect(jsonPath("links[1]['rel']").value("user"))
            .andExpect(jsonPath("links[2]['rel']").value("assignee"))
            .andExpect(jsonPath("links[3]['rel']").value("milestone"))
            .andExpect(jsonPath("links", hasSize<Int>(4)))
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não retornar nenhuma issue")
    fun throwErrorByNoIssueFound() {
        given(this.serviceImpl.getIssue()).willThrow(IssueNotFoundException("No issue found."))

        this.mockMvc.perform(getRequest(ISSUE))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("No issue found."))
    }

}