package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.RepositoryNotFoundException
import com.gga.webhook.models.vO.RepositoryVo
import com.gga.webhook.services.impls.RepositoryServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.REPOSITORY
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.hamcrest.Matchers.hasSize
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
internal class RepositoryControllerTest {

    @MockBean
    private lateinit var repositoryServiceImpl: RepositoryServiceImpl

    @Autowired
    private lateinit var repositoryController: RepositoryController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedRepository: RepositoryVo = PayloadBuilder().repositoryDto() convertTo RepositoryVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o repository com as mesmas propriedades que o esperado")
    fun verifyRepositoryBody() {
        `when`(this.repositoryServiceImpl.getRepository()).thenReturn((this.expectedRepository))

        this.repositoryController.getRepository().also {
            assertEquals(expectedRepository, it.body!!)
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar o repository com o HATEOAS configurado")
    fun verifyRepositoryLink() {
        given(this.repositoryServiceImpl.getRepository()).willReturn((this.expectedRepository))

        this.mockMvc.perform(getRequest(REPOSITORY))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
            .andExpect(jsonPath("links[1]['rel']").value("owner"))
            .andExpect(jsonPath("links[2]['rel']").value("license"))
            .andExpect(jsonPath("links", hasSize<Int>(3)))
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não retornar nenhum repository")
    fun throwErrorByNoRepositoryFound() {
        given(this.repositoryServiceImpl.getRepository()).willThrow(RepositoryNotFoundException("No repository found."))

        this.mockMvc.perform(getRequest(REPOSITORY))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("No repository found."))
    }

}