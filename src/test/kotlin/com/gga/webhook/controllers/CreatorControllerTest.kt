package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.CreatorNotFoundException
import com.gga.webhook.models.vO.CreatorVo
import com.gga.webhook.services.impls.CreatorServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.CREATOR
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
internal class CreatorControllerTest {

    @MockBean
    private lateinit var creatorServiceImpl: CreatorServiceImpl

    @Autowired
    private lateinit var creatorController: CreatorController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedCreator: CreatorVo = PayloadBuilder().creatorDto() convertTo CreatorVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o creator com as mesmas propriedades que o esperado")
    fun verifyCreatorBody() {
        `when`(this.creatorServiceImpl.getCreator()).thenReturn((this.expectedCreator))

        this.creatorController.getCreator().also { assertEquals(expectedCreator, it.body!!) }
    }

    @Test
    @DisplayName("GET -> Deve retornar o creator com o HATEOAS configurado")
    fun verifyCreatorLink() {
        given(this.creatorServiceImpl.getCreator()).willReturn((this.expectedCreator))

        this.mockMvc.perform(getRequest(CREATOR))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não retornar nenhum creator")
    fun throwErrorByNoCreatorFound() {
        given(this.creatorServiceImpl.getCreator()).willThrow(CreatorNotFoundException("No creator found."))

        this.mockMvc.perform(getRequest(CREATOR))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("No creator found."))
    }

}