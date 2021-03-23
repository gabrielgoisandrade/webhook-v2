package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.UserDto
import com.gga.webhook.services.impls.UserServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.USER
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController::class])
internal class UserControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: UserServiceImpl

    @Autowired
    private lateinit var controller: UserController

    private val expected: UserDto = this.dto.userDto()

    @Test
    fun findUserByIssueNumber() {
        `when`(this.service.findUserByIssueNumber(anyInt())).thenReturn(this.expected)

        this.controller.findUserByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        given(this.service.findUserByIssueNumber(anyInt()))
            .willThrow(RelationNotFoundException("There isn't any User related with this Issue"))

        this.mockMvc.perform(getRequest("$USER/issue/$ISSUE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any User related with this Issue"))
    }

}
