package com.gga.webhook.repositories

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.LicenseModel
import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.UserModel
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.models.dTO.UserDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions
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
internal class UserRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var userRepository: UserRepository

    private val builder: PayloadBuilder = PayloadBuilder()

    private val payload: PayloadDto = this.builder.payload()

    private val userDto: UserDto = this.builder.userDto()

    @Test
    @DisplayName("Deve persistir User no database")
    fun saveUser() {
        this.entityManager.persist(this.userDto convertTo UserModel::class.java)

        this.userRepository.findAll().also { Assertions.assertTrue(it.isNotEmpty()) }
    }

    @Test
    @DisplayName("Deve retornar o user de determinada issue")
    fun getUser() {
        val persist: PayloadModel = this.entityManager.persist((this.payload convertTo PayloadModel::class.java).apply {
            this.repository!!.license = entityManager.merge(builder.licenseDto() convertTo LicenseModel::class.java)
        })

        val user: UserModel = this.userRepository.getUser()!!

        assertEquals(persist.issue!!.user, user)
    }

}