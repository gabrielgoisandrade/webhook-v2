package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.vO.AssigneeVo
import com.gga.webhook.services.impls.AssigneeServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.ASSIGNEE
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class AssigneeControllerTest {

    @MockBean
    private lateinit var assigneeServiceImpl: AssigneeServiceImpl

    @Autowired
    private lateinit var assigneeController: AssigneeController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedAssignee: AssigneeVo = PayloadBuilder().assigneeDto() convertTo AssigneeVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o assignee com as mesmas propriedades que o esperado")
    fun verifyAssigneeBody() {
        `when`(this.assigneeServiceImpl.getAssignee()).thenReturn((this.expectedAssignee))

        this.assigneeController.getAssignee().also { assertEquals(expectedAssignee, it.body!!) }
    }

    @Test
    @DisplayName("GET -> Deve retornar o assignee com o HATEOAS configurado")
    fun verifyAssigneeLink() {
        given(this.assigneeServiceImpl.getAssignee()).willReturn((this.expectedAssignee))

        this.mockMvc.perform(getRequest(ASSIGNEE))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve status code 204 por não haver assignee na issue")
    fun getNullAssignee() {
        given(this.assigneeServiceImpl.getAssignee()).willReturn(null)

        this.mockMvc.perform(getRequest(ASSIGNEE)).andExpect(status().isNoContent)
    }

}