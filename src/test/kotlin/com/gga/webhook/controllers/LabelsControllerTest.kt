package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.ASSIGNEES_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.LABELS_CONTROLLER
import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.services.impls.LabelsServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.LABELS
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [LabelsController::class])
internal class LabelsControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: LabelsServiceImpl

    @Autowired
    private lateinit var controller: LabelsController

    private val expected: LabelsDto = this.dto.labelsDto()

    @Test
    fun findLabelsByIssueNumber() {
        val expected: Page<LabelsDto> = PageImpl(listOf(this.expected))

        val link: Link = linkTo(methodOn(LABELS_CONTROLLER).findLabelsByIssueNumber(ISSUE_NUMBER)).withSelfRel()

        val expectedPage: CollectionModel<LabelsDto> = CollectionModel.of(expected, link)

        `when`(this.service.findLabelsByIssueNumber(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(expected)

        this.controller.findLabelsByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body!!).isNotEmpty.isEqualTo(expectedPage)
            assertThat(it.body!!.links.getLink("self").isPresent).isTrue

            it.body!!.content.forEach { content: LabelsDto ->
                assertThat(content.links.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        given(this.service.findLabelsByIssueNumber(anyInt(), anyInt(), anyInt(), anyString()))
            .willThrow(RelationNotFoundException("There isn't any Labels related with this Issue"))

        this.mockMvc.perform(getRequest("$LABELS/issue/$ISSUE_NUMBER"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any Labels related with this Issue"))
    }

}
