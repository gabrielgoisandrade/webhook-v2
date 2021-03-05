package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.dTO.LicenseDto
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class LicenseRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var licenseRepository: LicenseRepository

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payload: PayloadDto = this.builder.payload()

    private val licenseDto: LicenseDto = this.builder.licenseDto()

    @Test
    @DisplayName("Deve persistir License no database")
    fun saveLicense() {
        this.entityManager.merge(this.licenseDto convertTo LicenseModel::class.java)

        this.licenseRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve retornar a license de determinado repository")
    fun getOwner() {
        val persist: PayloadModel = this.entityManager.persist((this.payload convertTo PayloadModel::class.java).apply {
            this.repository!!.license = entityManager.merge(builder.licenseDto() convertTo LicenseModel::class.java)
        })

        val license: LicenseModel = this.licenseRepository.getLicense()!!

        assertEquals(persist.repository!!.license, license)
    }

}