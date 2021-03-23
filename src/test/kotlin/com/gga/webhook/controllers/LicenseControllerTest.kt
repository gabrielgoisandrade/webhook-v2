package com.gga.webhook.controllers

import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseControllerTestFactory
import com.gga.webhook.models.dTO.LicenseDto
import com.gga.webhook.services.impls.LicenseServiceImpl
import com.gga.webhook.utils.RequestUtil.Companion.LICENSE
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

@WebMvcTest(controllers = [LicenseController::class])
internal class LicenseControllerTest : BaseControllerTestFactory() {

    @MockBean
    private lateinit var service: LicenseServiceImpl

    @Autowired
    private lateinit var controller: LicenseController

    private val expected: LicenseDto = this.dto.licenseDto()

    @Test
    fun findLicenseByRepositoryName() {
        `when`(this.service.findLicenseByRepositoryName(anyString())).thenReturn(this.expected)

        this.controller.findLicenseByRepositoryName(REPOSITORY_NAME).also {
            assertThat(it.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(it.body).isEqualTo(this.expected)

            with(it.body!!.links) {
                assertThat(this.isEmpty).isFalse
                assertThat(this.getLink("self").isPresent).isTrue
            }
        }
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        given(this.service.findLicenseByRepositoryName(anyString()))
            .willThrow(RelationNotFoundException("There isn't any License related with this Repository"))

        this.mockMvc.perform(getRequest("$LICENSE/repository/$REPOSITORY_NAME"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("message").value("There isn't any License related with this Repository"))
    }

}
