package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.MILESTONE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.CreatorDto
import com.gga.webhook.services.impls.CreatorServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.CREATOR
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

@WebMvcTest(controllers = [CreatorController::class])
internal class CreatorControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: CreatorServiceImpl

    @Autowired
    private lateinit var controller: CreatorController

    private val expected: CreatorDto = this.dto.creatorDto()

    @Test
    fun findCreatorByMilestoneNumber() {
        `when`(this.service.findCreatorByMilestoneNumber(anyInt())).thenReturn(this.expected)

        this.controller.findCreatorByMilestoneNumber(MILESTONE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByMilestoneNumberNotFound() {
        given(this.service.findCreatorByMilestoneNumber(anyInt()))
            .willThrow(RelationNotFoundException("There isn't any Creator related with this Milestone."))

        this.mockMvc.perform(getRequest("$CREATOR/milestone/$MILESTONE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Creator related with this Milestone."))
    }

}
