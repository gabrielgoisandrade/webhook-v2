package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.vO.LicenseVo
import com.gga.webhook.repositories.LicenseRepository
import com.gga.webhook.services.impls.LicenseServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class LicenseServiceImplTest {

    @MockBean
    private lateinit var licenseRepository: LicenseRepository

    private val licenseServiceImpl: LicenseServiceImpl by lazy { LicenseServiceImpl(this.licenseRepository) }

    private val expectedLicense: LicenseVo = PayloadBuilder().licenseDto() convertTo LicenseVo::class.java

    @Test
    @DisplayName("Deve retornar o license de determinado repository")
    fun getLicense() {
        `when`(this.licenseRepository.getLicense())
            .thenReturn(expectedLicense convertTo LicenseModel::class.java)

        val licenseVo: LicenseVo? = this.licenseServiceImpl.getLicense()

        assertEquals(expectedLicense, licenseVo)
    }

}