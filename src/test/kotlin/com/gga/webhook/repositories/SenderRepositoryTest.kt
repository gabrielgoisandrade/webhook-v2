package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.SenderModel
import com.gga.webhook.utils.MapperUtil.Companion.toModel
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
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
    private lateinit var payloadRepository: PayloadRepository

    @Autowired
    private lateinit var senderRepository: SenderRepository

    private val builder: PayloadBuilder = PayloadBuilder()

    @Test
    fun saveSender() {
        val senderModel: SenderModel = this.builder.senderDto() convertTo SenderModel::class.java

        val payloadModel: PayloadModel = this.builder.payload().toModel().apply {
            this.issue = null
            this.repository = null
            this.sender = null
        }

        this.entityManager.persist(payloadModel)

        this.entityManager.persist(senderModel)

        payloadModel.sender = senderModel
        this.entityManager.persist(payloadModel)

        println(payloadRepository.findAll().first())
        println(senderRepository.findAll().first())
    }

}
