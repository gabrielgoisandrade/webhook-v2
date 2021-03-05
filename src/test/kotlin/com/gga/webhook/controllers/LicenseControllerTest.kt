package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.vO.LicenseVo
import com.gga.webhook.services.impls.LicenseServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.LICENSE
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
internal class LicenseControllerTest {

    @MockBean
    private lateinit var licenseServiceImpl: LicenseServiceImpl

    @Autowired
    private lateinit var licenseController: LicenseController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedLicense: LicenseVo = PayloadBuilder().licenseDto() convertTo LicenseVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o license com as mesmas propriedades que o esperado")
    fun verifyLicenseBody() {
        `when`(this.licenseServiceImpl.getLicense()).thenReturn((this.expectedLicense))

        this.licenseController.getLicense().also { assertEquals(expectedLicense, it.body!!) }
    }

    @Test
    @DisplayName("GET -> Deve retornar o license com o HATEOAS configurado")
    fun verifyLicenseLink() {
        given(this.licenseServiceImpl.getLicense()).willReturn((this.expectedLicense))

        this.mockMvc.perform(getRequest(LICENSE))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
    }

    @Test
    @DisplayName("GET -> Deve status code 204 por não haver license no repository")
    fun getNullLicense() {
        given(this.licenseServiceImpl.getLicense()).willReturn(null)

        this.mockMvc.perform(getRequest(LICENSE)).andExpect(status().isNoContent)
    }

}