package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.LicenseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class LicenseRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: LicenseRepository

    private val licenseModel: LicenseModel = this.model.license

    @Test
    @DisplayName("Must save License")
    fun saveLicense() {
        val expectedLicense: LicenseModel = this.entityManager.merge(this.licenseModel)

        this.repository.findById(expectedLicense.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedLicense)
        }
    }

    @Test
    @DisplayName("Must return the License by key")
    fun findByKey() {
        val expectedLicense: LicenseModel = this.entityManager.merge(this.licenseModel)

        this.repository.findByKey(expectedLicense.key).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedLicense)
        }
    }

}