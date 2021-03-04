package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
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
internal class SenderRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var senderRepository: SenderRepository

    private val builder: PayloadBuilder = PayloadBuilder()
    private val payload: PayloadDto = this.builder.payload()

    @Test
    @DisplayName("Deve retornar os senders de determinado payload")
    fun getSenders() {
        val persist: PayloadModel = this.entityManager.persist((this.payload convertTo PayloadModel::class.java).apply {
            this.repository!!.license = entityManager.merge(builder.licenseDto() convertTo LicenseModel::class.java)
        })

        val senders: SenderModel = this.senderRepository.getSender()!!

        assertEquals(persist.sender, senders)
    }
}