package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.UserNotFoundException
import com.gga.webhook.models.vO.UserVo
import com.gga.webhook.services.impls.UserServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.USER
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
internal class UserControllerTest {

    @MockBean
    private lateinit var userServiceImpl: UserServiceImpl

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedUser: UserVo = PayloadBuilder().userDto() convertTo UserVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o user com as mesmas propriedades que o esperado")
    fun verifyUserBody() {
        `when`(this.userServiceImpl.getUser()).thenReturn((this.expectedUser))

        this.userController.getUser().also { assertEquals(expectedUser, it.body!!) }
    }

    @Test
    @DisplayName("GET -> Deve retornar o user com o HATEOAS configurado")
    fun verifyUserLink() {
        given(this.userServiceImpl.getUser()).willReturn((this.expectedUser))

        this.mockMvc.perform(getRequest(USER))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve retornar um erro ao não retornar nenhum user")
    fun throwErrorByNoUserFound() {
        given(this.userServiceImpl.getUser()).willThrow(UserNotFoundException("No user found."))

        this.mockMvc.perform(getRequest(USER))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("No user found."))
    }

}