package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.dTO.CreatorDto
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
internal class CreatorRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var creatorRepository: CreatorRepository

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payload: PayloadDto = this.builder.payload()

    private val creatorDto: CreatorDto = this.builder.creatorDto()

    @Test
    @DisplayName("Deve persistir Creator no database")
    fun saveCreator() {
        this.entityManager.persist(this.creatorDto convertTo CreatorModel::class.java)

        this.creatorRepository.findAll().also { assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve retornar o creator de determinado milestone")
    fun getCreator() {
        val persist: PayloadModel = this.entityManager.persist((this.payload convertTo PayloadModel::class.java).apply {
            this.repository!!.license = entityManager.merge(builder.licenseDto() convertTo LicenseModel::class.java)
        })

        val creator: CreatorModel = this.creatorRepository.getCreator()!!

        assertEquals(persist.issue!!.milestone!!.creator, creator)
    }

}