package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.REPOSITORY_NAME
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.dTO.LicenseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*

internal class LicenseServiceImplTest : BaseServiceImplTestFactory() {

    private val service: LicenseServiceImpl by lazy { LicenseServiceImpl(this.licenseRepository) }

    private val expectedModel: LicenseModel = this.model.license

    private val expectedDto: LicenseDto = this.dto.licenseDto()

    @Test
    fun saveLicense() {
        `when`(this.licenseRepository.findByKey(anyString())).thenReturn(Optional.empty())

        `when`(this.licenseRepository.save(any(LicenseModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveLicense(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingLicense() {
        `when`(this.licenseRepository.findByKey(anyString())).thenReturn(Optional.of(this.expectedModel))

        verify(this.licenseRepository, never()).save(any(LicenseModel::class.java))

        this.service.saveLicense(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun findLicenseByRepositoryName() {
        `when`(this.licenseRepository.findByRepositoryName(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.findLicenseByRepositoryName(REPOSITORY_NAME).also { assertThat(it).isEqualTo(this.expectedDto) }
    }

    @Test
    fun throwErrorByRepositoryNameNotFound() {
        `when`(this.licenseRepository.findByRepositoryName(anyString()))
            .thenThrow(RelationNotFoundException("There isn't any License with this Repository"))

        assertThrows<RelationNotFoundException> { this.service.findLicenseByRepositoryName(REPOSITORY_NAME) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any License with this Repository")
        }
    }

}
