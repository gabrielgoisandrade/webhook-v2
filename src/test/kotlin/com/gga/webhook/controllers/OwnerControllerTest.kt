package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.OwnerNotFoundException
import com.gga.webhook.models.vO.OwnerVo
import com.gga.webhook.services.impls.OwnerServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.OWNER
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
internal class OwnerControllerTest {

    @MockBean
    private lateinit var ownerServiceImpl: OwnerServiceImpl

    @Autowired
    private lateinit var ownerController: OwnerController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedOwner: OwnerVo = PayloadBuilder().ownerDto() convertTo OwnerVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o owner com as mesmas propriedades que o esperado")
    fun verifyOwnerBody() {
        `when`(this.ownerServiceImpl.getOwner()).thenReturn((this.expectedOwner))

        this.ownerController.getOwner().also {
            assertEquals(expectedOwner, it.body!!)
        }
    }

    @Test
    @DisplayName("GET -> Deve retornar o owner com o HATEOAS configurado")
    fun verifyOwnerLink() {
        given(this.ownerServiceImpl.getOwner()).willReturn((this.expectedOwner))

        this.mockMvc.perform(getRequest(OWNER))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não retornar nenhum owner")
    fun throwErrorByNoOwnerFound() {
        given(this.ownerServiceImpl.getOwner()).willThrow(OwnerNotFoundException("No owner found."))

        this.mockMvc.perform(getRequest(OWNER))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("No owner found."))
    }

}